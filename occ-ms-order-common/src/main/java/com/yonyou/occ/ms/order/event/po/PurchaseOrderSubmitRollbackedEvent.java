package com.yonyou.occ.ms.order.event.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order submit is rollbacked.
 *
 * @author WangRui
 * @date 2018-01-17 11:25:20
 */
@Value
public class PurchaseOrderSubmitRollbackedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;
}
