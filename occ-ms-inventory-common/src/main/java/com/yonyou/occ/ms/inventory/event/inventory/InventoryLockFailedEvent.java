package com.yonyou.occ.ms.inventory.event.inventory;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.Value;

/**
 * The event occurs when the lock of inventory is reverted.
 *
 * @author WangRui
 * @date 2018-01-08 14:12:44
 */
@Value
public class InventoryLockFailedEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final PurchaseOrderId purchaseOrderId;

    private final ProductId productId;
}
