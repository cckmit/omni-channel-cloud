package com.yonyou.occ.ms.customer.event.customeraccount;

import com.yonyou.occ.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.customer.vo.CustomerAccountId;
import lombok.Value;

/**
 * The event occurs when a account for customer is deleted.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Value
public class CustomerAccountDeletedEvent extends AbstractDomainEvent {
    private final CustomerAccountId id;
}
