package com.yonyou.occ.ms.inventory.command.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to create the inventory of a product.
 *
 * @author WangRui
 * @date 2018-01-08 11:19:12
 */
@Data
@AllArgsConstructor
public class CreateInventoryCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private InventoryId id;

    private ProductId productId;

    private BigDecimal quantity;
}
