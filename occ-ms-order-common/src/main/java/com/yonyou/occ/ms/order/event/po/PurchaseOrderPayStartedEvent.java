package com.yonyou.occ.ms.order.event.po;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order is started to be paid.
 *
 * @author WangRui
 * @date 2018-01-10 09:01:56
 */
@Value
public class PurchaseOrderPayStartedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;

    private final CustomerAccount customerAccount;

    private final BigDecimal amount;
}
