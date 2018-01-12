package com.yonyou.occ.ms.order.event.po;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.order.PoPaymentId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order is paid failed.
 *
 * @author WangRui
 * @date 2018-01-12 13:48:28
 */
@Value
public class PurchaseOrderPayFailedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;

    private PoPaymentId poPaymentId;

    private final CustomerAccount customerAccount;

    private final BigDecimal amount;
}
