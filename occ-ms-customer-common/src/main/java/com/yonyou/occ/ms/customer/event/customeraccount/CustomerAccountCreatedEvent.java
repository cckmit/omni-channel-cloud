package com.yonyou.occ.ms.customer.event.customeraccount;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerId;
import lombok.Value;

/**
 * The event occurs when a account for customer is created.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Value
public class CustomerAccountCreatedEvent extends AbstractDomainEvent {
    private final CustomerAccountId id;

    private final CustomerId customerId;

    private final String code;

    private final String name;

    private final BigDecimal credit;
}
