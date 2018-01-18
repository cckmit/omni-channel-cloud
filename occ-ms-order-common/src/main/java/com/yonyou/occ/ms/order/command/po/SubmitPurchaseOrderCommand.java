package com.yonyou.occ.ms.order.command.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to submit a purchase order.
 *
 * @author WangRui
 * @date 2018-01-16 17:31:16
 */
@Data
@AllArgsConstructor
public class SubmitPurchaseOrderCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private PurchaseOrderId id;
}
