package com.yonyou.occ.ms.order.command.aggregate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import com.yonyou.occ.ms.common.domain.vo.customer.Customer;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.order.PoItem;
import com.yonyou.occ.ms.common.domain.vo.order.PoPayment;
import com.yonyou.occ.ms.common.domain.vo.order.PoPaymentId;
import com.yonyou.occ.ms.common.domain.vo.order.PoTypeId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.order.enums.PoStateEnum;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderCreatedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderDeletedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPayFailedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPayStartedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderPaySuccessfulEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitConfirmedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmitRollbackedEvent;
import com.yonyou.occ.ms.order.event.po.PurchaseOrderSubmittedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * The purchase order aggregate class.
 *
 * @author WangRui
 * @date 2018-01-10 08:52:28
 */
@Slf4j
@NoArgsConstructor
@Aggregate
public class PurchaseOrderAggregate {
    @AggregateIdentifier
    private PurchaseOrderId id;

    private String code;

    private ZonedDateTime orderDate;

    private BigDecimal totalAmount;

    private PoTypeId poTypeId;

    private Customer customer;

    private CustomerAccount customerAccount;

    private PoStateEnum poState;

    @AggregateMember
    private List<PoItem> poItems;

    @AggregateMember
    private List<PoPayment> poPayments;

    public PurchaseOrderAggregate(PurchaseOrderId id, String code, PoTypeId poTypeId, Customer customer,
        CustomerAccount customerAccount, List<PoItem> poItems) {
        computeTotalAmount();

        apply(new PurchaseOrderCreatedEvent(id, code, totalAmount, poTypeId, customer, customerAccount, poItems));
    }

    public void startPay() {
        apply(new PurchaseOrderPayStartedEvent(id, customerAccount, totalAmount));
    }

    public void failPay(PoPaymentId poPaymentId) {
        apply(new PurchaseOrderPayFailedEvent(id, poPaymentId, customerAccount, totalAmount));
    }

    public void successPay(PoPaymentId poPaymentId) {
        apply(new PurchaseOrderPaySuccessfulEvent(id, poPaymentId, customerAccount, totalAmount));
    }

    public void submit() {
        apply(new PurchaseOrderSubmittedEvent(id, poItems));
    }

    public void rollbackSubmit() {
        apply(new PurchaseOrderSubmitRollbackedEvent(id));
    }

    public void confirmSubmit() {
        apply(new PurchaseOrderSubmitConfirmedEvent(id));
    }

    public void delete() {
        // Marks this aggregate as deleted, instructing a repository to remove that aggregate at an appropriate time.
        markDeleted();

        apply(new PurchaseOrderDeletedEvent(id));
    }

    private void computeTotalAmount() {
        poItems.forEach(poItem -> totalAmount = totalAmount.add(poItem.getPrice().multiply(poItem.getQuantity())));
    }

    @EventSourcingHandler
    private void on(PurchaseOrderCreatedEvent event) {
        id = event.getId();
        code = event.getCode();
        orderDate = ZonedDateTime.now();
        poTypeId = event.getPoTypeId();
        customer = event.getCustomer();
        customerAccount = event.getCustomerAccount();
        poState = PoStateEnum.CREATED;
        poItems = event.getPoItems();

        log.debug("Applied: 'PurchaseOrderCreatedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderPayStartedEvent event) {
        log.debug("Applied: 'PurchaseOrderPayStartedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderPayFailedEvent event) {
        PoPayment poPayment = new PoPayment(event.getPoPaymentId(), event.getAmount(), false, "", ZonedDateTime.now());
        poPayments.add(poPayment);

        log.debug("Applied: 'PurchaseOrderPayFailedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderPaySuccessfulEvent event) {
        poState = PoStateEnum.PAID;

        PoPayment poPayment = new PoPayment(event.getPoPaymentId(), event.getAmount(), true, "", ZonedDateTime.now());
        poPayments.add(poPayment);

        log.debug("Applied: 'PurchaseOrderPaySuccessfulEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderSubmittedEvent event) {
        poState = PoStateEnum.SUBMITTED;

        log.debug("Applied: 'PurchaseOrderSubmittedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderSubmitRollbackedEvent event) {
        poState = PoStateEnum.PAID;

        log.debug("Applied: 'PurchaseOrderSubmitRollbackedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderSubmitConfirmedEvent event) {
        poState = PoStateEnum.SUBMITTED;

        log.debug("Applied: 'PurchaseOrderSubmitConfirmedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(PurchaseOrderDeletedEvent event) {
        log.debug("Applied: 'PurchaseOrderDeletedEvent' [{}]", event);
    }
}
