package com.yonyou.occ.ms.inventory.event.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import lombok.Value;

/**
 * The event occurs when the inventory of a product is increased.
 *
 * @author WangRui
 * @date 2018-01-08 14:12:44
 */
@Value
public class InventoryIncreasedEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final BigDecimal quantity;
}
