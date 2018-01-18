package com.yonyou.occ.ms.order.command.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to confirm the submit a purchase order.
 *
 * @author WangRui
 * @date 2018-01-17 11:22:51
 */
@Data
@AllArgsConstructor
public class ConfirmSubmitPurchaseOrderCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private PurchaseOrderId id;
}
