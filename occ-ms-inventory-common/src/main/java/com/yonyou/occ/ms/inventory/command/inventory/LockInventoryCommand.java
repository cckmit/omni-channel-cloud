package com.yonyou.occ.ms.inventory.command.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to lock some quantity of a inventory of product.
 *
 * @author WangRui
 * @date 2018-01-08 14:05:16
 */
@Data
@AllArgsConstructor
public class LockInventoryCommand extends AbstractDomainCommand {
    /**
     * The ID of the inventory aggregate.
     */
    @TargetAggregateIdentifier
    private InventoryId id;

    /**
     * The ID of the purchase order item.
     */
    private PoItemId poItemId;

    /**
     * The quantity of the inventory to lock.
     */
    private BigDecimal quantity;
}
