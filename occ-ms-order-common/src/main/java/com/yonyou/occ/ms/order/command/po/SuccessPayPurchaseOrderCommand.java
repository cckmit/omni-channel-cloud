package com.yonyou.occ.ms.order.command.po;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.order.PoPaymentId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to finish pay a purchase order successfully.
 *
 * @author WangRui
 * @date 2018-01-12 13:46:36
 */
@Data
@AllArgsConstructor
public class SuccessPayPurchaseOrderCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private PurchaseOrderId id;

    private PoPaymentId poPaymentId;
}
