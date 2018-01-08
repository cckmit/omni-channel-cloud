package com.yonyou.occ.ms.customer.command.customeraccount;

import java.math.BigDecimal;

import com.yonyou.occ.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.customer.vo.CustomerAccountId;
import com.yonyou.occ.ms.customer.vo.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to create a account for customer.
 *
 * @author WangRui
 * @date 2018-01-05 08:54:26
 */
@Data
@AllArgsConstructor
public class CreateCustomerAccountCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private CustomerAccountId id;

    private CustomerId customerId;

    private String code;

    private String name;

    private BigDecimal credit;
}
