package com.yonyou.occ.ms.customer.command.aggregate;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreatedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditDecreasedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditIncreasedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditNotEnoughEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountDeletedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * The customer account aggregate class.
 *
 * @author WangRui
 * @date 2018-01-05 10:21:50
 */
@Slf4j
@NoArgsConstructor
@Aggregate
public class CustomerAccountAggregate {
    @AggregateIdentifier
    private CustomerAccountId id;

    private CustomerId customerId;

    private String code;

    private String name;

    private BigDecimal credit;

    public CustomerAccountAggregate(CustomerAccountId id, CustomerId customerId, String code, String name,
        BigDecimal credit) {
        apply(new CustomerAccountCreatedEvent(id, customerId, code, name, credit));
    }

    public void update(String code, String name) {
        apply(new CustomerAccountUpdatedEvent(id, code, name));
    }

    public void increaseCredit(BigDecimal amount) {
        apply(new CustomerAccountCreditIncreasedEvent(id, amount));
    }

    public void decreaseCredit(PurchaseOrderId purchaseOrderId, BigDecimal amount) {
        if (credit.compareTo(amount) < 0) {
            apply(new CustomerAccountCreditNotEnoughEvent(id, purchaseOrderId));
        } else {
            apply(new CustomerAccountCreditDecreasedEvent(id, purchaseOrderId, amount));
        }
    }

    public void delete() {
        // Marks this aggregate as deleted, instructing a repository to remove that aggregate at an appropriate time.
        markDeleted();

        apply(new CustomerAccountDeletedEvent(id));
    }

    @EventSourcingHandler
    private void on(CustomerAccountCreatedEvent event) {
        id = event.getId();
        customerId = event.getCustomerId();
        code = event.getCode();
        name = event.getName();
        credit = event.getCredit();

        log.debug("Applied: 'CustomerAccountCreatedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void update(CustomerAccountUpdatedEvent event) {
        code = event.getCode();
        name = event.getName();

        log.debug("Applied: 'CustomerAccountUpdatedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(CustomerAccountCreditIncreasedEvent event) {
        credit = credit.add(event.getAmount());

        log.debug("Applied: 'CustomerAccountCreditIncreasedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(CustomerAccountCreditDecreasedEvent event) {
        credit = credit.subtract(event.getAmount());

        log.debug("Applied: 'CustomerAccountCreditDecreasedEvent' [{}]", event);
    }

    @EventSourcingHandler
    private void on(CustomerAccountDeletedEvent event) {
        this.id = event.getId();

        log.debug("Applied: 'CustomerAccountDeletedEvent' [{}]", event);
    }
}
