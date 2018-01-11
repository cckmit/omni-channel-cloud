package com.yonyou.occ.ms.customer.event.customeraccount;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when the credit of a customer's account is not enough to decrease.
 *
 * @author WangRui
 * @date 2018-01-10 11:31:35
 */
@Value
public class CustomerAccountCreditNotEnoughEvent extends AbstractDomainEvent {
    private final CustomerAccountId id;

    private final PurchaseOrderId purchaseOrderId;

}
