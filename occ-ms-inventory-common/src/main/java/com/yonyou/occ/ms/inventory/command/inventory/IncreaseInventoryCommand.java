package com.yonyou.occ.ms.inventory.command.inventory;

import java.math.BigDecimal;

import com.yonyou.occ.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.inventory.vo.InventoryId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to increase the inventory of product.
 *
 * @author WangRui
 * @date 2018-01-08 14:01:52
 */
@Data
@AllArgsConstructor
public class IncreaseInventoryCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private InventoryId id;

    private BigDecimal quantity;
}
