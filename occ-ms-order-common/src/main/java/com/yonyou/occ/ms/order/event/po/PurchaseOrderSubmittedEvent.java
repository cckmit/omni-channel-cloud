package com.yonyou.occ.ms.order.event.po;

import java.util.List;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.order.PoItem;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order is submitted.
 *
 * @author WangRui
 * @date 2018-01-16 17:32:20
 */
@Value
public class PurchaseOrderSubmittedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;

    private final List<PoItem> poItems;
}
