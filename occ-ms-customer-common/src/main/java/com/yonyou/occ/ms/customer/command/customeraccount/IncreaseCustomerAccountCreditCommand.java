package com.yonyou.occ.ms.customer.command.customeraccount;

import java.math.BigDecimal;

import com.yonyou.occ.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.customer.vo.CustomerAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to increase the credit of customer's account.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Data
@AllArgsConstructor
public class IncreaseCustomerAccountCreditCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private CustomerAccountId id;

    private BigDecimal amount;
}
