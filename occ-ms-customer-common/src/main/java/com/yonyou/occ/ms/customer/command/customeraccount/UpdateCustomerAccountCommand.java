package com.yonyou.occ.ms.customer.command.customeraccount;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to update a account for customer.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Data
@AllArgsConstructor
public class UpdateCustomerAccountCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private CustomerAccountId id;

    private String code;

    private String name;
}
