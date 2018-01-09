package com.yonyou.occ.ms.inventory.event.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.inventory.vo.InventoryId;
import com.yonyou.occ.ms.inventory.vo.PoItemId;
import lombok.Value;

/**
 * The event occurs when the inventory of a product is locked.
 *
 * @author WangRui
 * @date 2018-01-08 14:12:44
 */
@Value
public class InventoryLockedEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final PoItemId poItemId;

    private final BigDecimal quantity;
}
