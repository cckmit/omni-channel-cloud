package com.yonyou.occ.ms.inventory.command.aggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import com.yonyou.occ.ms.inventory.command.exception.InventoryNotEnoughException;
import com.yonyou.occ.ms.inventory.enums.OperationTypeEnum;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryCreatedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryIncreasedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockConfirmedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockRevertedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * The customer account aggregate class.
 *
 * @author WangRui
 * @date 2018-01-08 16:35:42
 */
@Slf4j
@NoArgsConstructor
@Aggregate
public class InventoryAggregate {
    @AggregateIdentifier
    private InventoryId id;

    private ProductId productId;

    private BigDecimal toSellQuantity;

    private BigDecimal lockedQuantity;

    private BigDecimal saledQuantity;

    private Boolean isEnabled;

    @AggregateMember
    private Map<PoItemId, InventoryLockLog> lockLogs;

    @AggregateMember
    private List<InventoryOperationLog> operationLogs;

    public InventoryAggregate(InventoryId id, ProductId productId, BigDecimal quantity) {
        apply(new InventoryCreatedEvent(id, productId, quantity));
    }

    public void increase(BigDecimal quantity) {
        apply(new InventoryIncreasedEvent(id, quantity));
    }

    public void lock(PoItemId poItemId, BigDecimal quantity) {
        if (toSellQuantity.compareTo(quantity) < 0) {
            throw new InventoryNotEnoughException("The product's inventory(" + id + ") is not enough to lock.");
        }

        apply(new InventoryLockedEvent(id, poItemId, quantity));
    }

    public void revertLock(PoItemId poItemId) {
        apply(new InventoryLockRevertedEvent(id, poItemId));
    }

    public void confirmLock(PoItemId poItemId) {
        apply(new InventoryLockConfirmedEvent(id, poItemId));
    }

    @EventSourcingHandler
    private void on(InventoryCreatedEvent event) {
        id = event.getId();
        productId = event.getProductId();
        toSellQuantity = event.getQuantity();
        lockedQuantity = BigDecimal.ZERO;
        saledQuantity = BigDecimal.ZERO;
        isEnabled = true;
        operationLogs = new ArrayList<>();
        lockLogs = new HashMap<>(16);

        InventoryOperationLog operationLog = new InventoryOperationLog(OperationTypeEnum.CREATE, event.getQuantity());
        operationLogs.add(operationLog);

        log.debug("Applied: 'InventoryCreatedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(InventoryIncreasedEvent event) {
        toSellQuantity = toSellQuantity.add(event.getQuantity());

        InventoryOperationLog operationLog = new InventoryOperationLog(OperationTypeEnum.INCREASE, event.getQuantity());
        operationLogs.add(operationLog);

        log.debug("Applied: 'InventoryIncreasedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(InventoryLockedEvent event) {
        toSellQuantity = toSellQuantity.subtract(event.getQuantity());
        lockedQuantity = lockedQuantity.add(event.getQuantity());

        InventoryOperationLog operationLog = new InventoryOperationLog(OperationTypeEnum.LOCK, event.getQuantity());
        operationLogs.add(operationLog);

        InventoryLockLog lockLog = new InventoryLockLog(event.getQuantity(), event.getPoItemId());
        lockLogs.put(event.getPoItemId(), lockLog);

        log.debug("Applied: 'InventoryLockedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(InventoryLockRevertedEvent event) {
        InventoryLockLog lockLog = lockLogs.get(event.getPoItemId());

        toSellQuantity = toSellQuantity.add(lockLog.getLockedQuantity());
        lockedQuantity = lockedQuantity.subtract(lockLog.getLockedQuantity());

        InventoryOperationLog operationLog = new InventoryOperationLog(OperationTypeEnum.REVERT_LOCK,
            lockLog.getLockedQuantity());
        operationLogs.add(operationLog);

        lockLogs.remove(event.getPoItemId());

        log.debug("Applied: 'InventoryLockRevertedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(InventoryLockConfirmedEvent event) {
        InventoryLockLog lockLog = lockLogs.get(event.getPoItemId());

        lockedQuantity = lockedQuantity.subtract(lockLog.getLockedQuantity());
        saledQuantity = saledQuantity.add(lockLog.getLockedQuantity());

        InventoryOperationLog operationLog = new InventoryOperationLog(OperationTypeEnum.CONFIRM_LOCK,
            lockLog.getLockedQuantity());
        operationLogs.add(operationLog);

        lockLogs.remove(event.getPoItemId());

        log.debug("Applied: 'InventoryLockConfirmedEvent' [{}]", event);
    }
}
