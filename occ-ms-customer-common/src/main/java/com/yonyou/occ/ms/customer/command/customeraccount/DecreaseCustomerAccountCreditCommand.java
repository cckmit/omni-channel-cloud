package com.yonyou.occ.ms.customer.command.customeraccount;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to decrease the credit of customer's account.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Data
@AllArgsConstructor
public class DecreaseCustomerAccountCreditCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private CustomerAccountId id;

    private PurchaseOrderId purchaseOrderId;

    private BigDecimal amount;
}
