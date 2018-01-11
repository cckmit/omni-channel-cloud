package com.yonyou.occ.ms.inventory.event.inventory;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import lombok.Value;

/**
 * The event occurs when the lock of inventory is reverted.
 *
 * @author WangRui
 * @date 2018-01-08 14:12:44
 */
@Value
public class InventoryLockRevertedEvent extends AbstractDomainEvent {
    private final InventoryId id;

    private final PoItemId poItemId;
}
