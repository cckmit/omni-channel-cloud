package com.yonyou.occ.ms.order.command.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to start to pay a purchase order.
 *
 * @author WangRui
 * @date 2018-01-09 16:21:06
 */
@Data
@AllArgsConstructor
public class StartPayPurchaseOrderCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private PurchaseOrderId id;
}
