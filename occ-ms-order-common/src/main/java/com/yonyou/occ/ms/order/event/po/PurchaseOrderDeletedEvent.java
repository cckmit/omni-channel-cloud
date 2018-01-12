package com.yonyou.occ.ms.order.event.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order is deleted.
 *
 * @author WangRui
 * @date 2018-01-10 09:01:56
 */
@Value
public class PurchaseOrderDeletedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;
}
