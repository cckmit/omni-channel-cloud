package com.yonyou.occ.ms.inventory.command.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to lock some quantity of a inventory of product.
 *
 * @author WangRui
 * @date 2018-01-08 14:05:16
 */
@Value
public class LockInventoryCommand extends AbstractDomainCommand {
    /**
     * The ID of the inventory aggregate.
     */
    @TargetAggregateIdentifier
    private final InventoryId id;

    /**
     * The ID of the purchase order.
     */
    private final PurchaseOrderId purchaseOrderId;

    /**
     * The ID of the purchase order item.
     */
    private final PoItemId poItemId;

    /**
     * The ID of the product.
     */
    private final ProductId productId;

    /**
     * The quantity of the inventory to lock.
     */
    private final BigDecimal quantity;
}
