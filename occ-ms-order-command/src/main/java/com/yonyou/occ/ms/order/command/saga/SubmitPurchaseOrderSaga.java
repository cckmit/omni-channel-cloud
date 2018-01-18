package com.yonyou.occ.ms.order.command.saga;

import java.util.HashMap;
import java.util.Map;

import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItem;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.inventory.command.inventory.LockInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.RevertLockInventoryCommand;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockConfirmedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockFailedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockRevertedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryNotEnoughEvent;
import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;
import com.yonyou.occ.ms.order.command.po.ConfirmSubmitPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.RollbackSubmitPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.web.rest.InventoryService;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitConfirmedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitRollbackedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmittedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * The process to submit a purchase order.
 *
 * @author WangRui
 * @date 2018-01-16 17:43:21
 */
@Slf4j
@Saga
public class SubmitPurchaseOrderSaga {
    private PurchaseOrderId purchaseOrderId;

    private Map<InventoryId, PoItem> toLock;

    private Map<InventoryId, PoItem> toRollback;

    private int toLockNumber;

    private boolean needRollback;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private InventoryService inventoryService;

    @StartSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderSubmittedEvent event) {
        purchaseOrderId = event.getId();

        toLock = new HashMap<>(8);
        event.getPoItems().forEach(poItem -> {
            String productId = poItem.getProduct().getId().getId();
            InventoryDTO inventory = inventoryService.getInventoryByProduct(productId).getBody();
            InventoryId inventoryId = new InventoryId(inventory.getId());
            toLock.put(inventoryId, poItem);

            LockInventoryCommand command = new LockInventoryCommand(inventoryId, purchaseOrderId, poItem.getId(),
                poItem.getProduct().getId(), poItem.getQuantity());
            commandGateway.send(command, new CommandCallback() {
                @Override
                public void onSuccess(CommandMessage commandMessage, Object result) {
                    log.debug("Send LockInventoryCommand successfully.");
                }

                @Override
                public void onFailure(CommandMessage commandMessage, Throwable cause) {
                    log.debug("Send LockInventoryCommand failed.");
                    if (commandMessage.getPayload() == null) {
                        log.error("Message payload is null!", cause);
                        return;
                    }
                    LockInventoryCommand cmd = (LockInventoryCommand) commandMessage.getPayload();
                    apply(new InventoryLockFailedEvent(cmd.getId(), cmd.getPurchaseOrderId(), cmd.getProductId()));
                }
            });
        });
        toLockNumber = toLock.size();
        toRollback = new HashMap<>(8);
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(InventoryNotEnoughEvent event) {
        log.debug("The inventory of product {} is not enough to be locked.", event.getProductId());

        toLockNumber--;
        needRollback = true;
        if (toLockNumber == 0) {
            tryFinish();
        }
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(InventoryLockFailedEvent event) {
        log.debug("Lock inventory of product {} failed.", event.getProductId());

        toLockNumber--;
        needRollback = true;
        if (toLockNumber == 0) {
            tryFinish();
        }
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(InventoryLockRevertedEvent event) {
        toRollback.remove(event.getId());
        if (toRollback.isEmpty()) {
            commandGateway.send(new RollbackSubmitPurchaseOrderCommand(event.getPurchaseOrderId()));
        }
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(InventoryLockConfirmedEvent event) {
        PoItem reservedPoItem = toLock.get(event.getId());
        reservedPoItem.setInventoryLocked(true);
        toLockNumber--;

        if (toLockNumber == 0) {
            tryFinish();
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderSubmitRollbackedEvent event) {
        log.debug("Purchase order {} submit is rollbacked.", event.getId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderSubmitConfirmedEvent event) {
        log.debug("Purchase order {} submit is confirmed.", event.getId());
    }

    /**
     * Try to finish the submit processing.
     */
    private void tryFinish() {
        if (needRollback) {
            toLock.forEach((inventoryId, poItem) -> {
                if (!poItem.isInventoryLocked()) {
                    return;
                }
                toRollback.put(inventoryId, poItem);
                commandGateway.send(new RevertLockInventoryCommand(inventoryId, purchaseOrderId, poItem.getId()));
            });
            if (toRollback.isEmpty()) {
                commandGateway.send(new RollbackSubmitPurchaseOrderCommand(purchaseOrderId));
            }
        } else {
            commandGateway.send(new ConfirmSubmitPurchaseOrderCommand(purchaseOrderId));
        }
    }
}
