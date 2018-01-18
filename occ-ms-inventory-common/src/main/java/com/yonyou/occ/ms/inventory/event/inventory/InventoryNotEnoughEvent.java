package com.yonyou.occ.ms.inventory.event.inventory;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.Value;

/**
 * The event occurs when the inventory is not enough to be locked.
 *
 * @author WangRui
 * @date 2018-01-17 10:10:35
 */
@Value
public class InventoryNotEnoughEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final PurchaseOrderId purchaseOrderId;

    private final PoItemId poItemId;

    private final ProductId productId;
}
