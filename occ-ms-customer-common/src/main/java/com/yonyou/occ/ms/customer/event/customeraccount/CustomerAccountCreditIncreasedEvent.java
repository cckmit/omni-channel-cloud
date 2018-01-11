package com.yonyou.occ.ms.customer.event.customeraccount;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import lombok.Value;

/**
 * The event occurs when the credit of a customer's account is increased.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Value
public class CustomerAccountCreditIncreasedEvent extends AbstractDomainEvent {
    private final CustomerAccountId id;

    private final BigDecimal amount;
}
