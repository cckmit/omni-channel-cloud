package com.yonyou.occ.ms.customer.command.customeraccount;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to delete a customer's account.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Data
@AllArgsConstructor
public class DeleteCustomerAccountCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private CustomerAccountId id;
}
