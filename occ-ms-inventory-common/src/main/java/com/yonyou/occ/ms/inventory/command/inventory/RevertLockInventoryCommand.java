package com.yonyou.occ.ms.inventory.command.inventory;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to revert the lock of product's inventory.
 *
 * @author WangRui
 * @date 2018-01-08 14:05:16
 */
@Data
@AllArgsConstructor
public class RevertLockInventoryCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private InventoryId id;

    private PoItemId poItemId;
}
