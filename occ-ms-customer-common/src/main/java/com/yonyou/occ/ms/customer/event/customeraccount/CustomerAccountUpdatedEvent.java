package com.yonyou.occ.ms.customer.event.customeraccount;

import com.yonyou.occ.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.customer.vo.CustomerAccountId;
import lombok.Value;

/**
 * The event occurs when a account for customer is updated.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Value
public class CustomerAccountUpdatedEvent extends AbstractDomainEvent {
    private final CustomerAccountId id;

    private final String code;

    private final String name;
}
