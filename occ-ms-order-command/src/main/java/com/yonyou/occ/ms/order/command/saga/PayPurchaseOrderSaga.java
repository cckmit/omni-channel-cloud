package com.yonyou.occ.ms.order.command.saga;

import java.util.UUID;

import com.yonyou.occ.ms.common.domain.vo.order.PoPaymentId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.customer.command.customeraccount.DecreaseCustomerAccountCreditCommand;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditDecreasedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditNotEnoughEvent;
import com.yonyou.occ.ms.order.command.po.FailPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.SuccessPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPayFailedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPayStartedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPaySuccessfulEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The process to pay a purchase order.
 *
 * @author WangRui
 * @date 2018-01-10 10:28:59
 */
@Slf4j
@Saga
public class PayPurchaseOrderSaga {
    private PurchaseOrderId purchaseOrderId;

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderPayStartedEvent event) {
        purchaseOrderId = event.getId();

        DecreaseCustomerAccountCreditCommand command = new DecreaseCustomerAccountCreditCommand(
            event.getCustomerAccount().getId(), event.getId(), event.getAmount());
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(CustomerAccountCreditNotEnoughEvent event) {
        log.debug("The credit of customer's account {} is not enough.", event.getId().getId());

        FailPayPurchaseOrderCommand command = new FailPayPurchaseOrderCommand(event.getPurchaseOrderId(),
            new PoPaymentId(UUID.randomUUID().toString()));
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "purchaseOrderId")
    public void handle(CustomerAccountCreditDecreasedEvent event) {
        log.debug("The credit of customer's account {} is decreased.", event.getId());

        SuccessPayPurchaseOrderCommand command = new SuccessPayPurchaseOrderCommand(event.getPurchaseOrderId(),
            new PoPaymentId(UUID.randomUUID().toString()));
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderPayFailedEvent event) {
        log.debug("The purchase order {} is paid failed.", event.getId().getId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id", keyName = "purchaseOrderId")
    public void handle(PurchaseOrderPaySuccessfulEvent event) {
        log.debug("The purchase order {} is paid successful.", event.getId().getId());
    }
}
