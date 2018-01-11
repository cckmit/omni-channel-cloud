package com.yonyou.occ.ms.inventory.event.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.Value;

/**
 * The event occurs when the inventory of a product is created.
 *
 * @author WangRui
 * @date 2018-01-08 11:22:59
 */
@Value
public class InventoryCreatedEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final ProductId productId;

    private final BigDecimal quantity;
}
