package com.yonyou.occ.ms.customer.command.handler;

import com.yonyou.occ.ms.customer.command.aggregate.CustomerAccountAggregate;
import com.yonyou.occ.ms.customer.command.customeraccount.CreateCustomerAccountCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.DecreaseCustomerAccountCreditCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.DeleteCustomerAccountCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.IncreaseCustomerAccountCreditCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.UpdateCustomerAccountCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.stereotype.Component;

/**
 * CustomerAccount command handler, all types of CustomerAccount commands are handled here.
 *
 * @author WangRui
 * @date 2018-01-05 11:03:56
 */
@Component
public class CustomerAccountCommandHandler {
    private final Repository<CustomerAccountAggregate> repository;

    public CustomerAccountCommandHandler(Repository<CustomerAccountAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreateCustomerAccountCommand command) throws Exception {
        repository.newInstance(
                () -> new CustomerAccountAggregate(command.getId(), command.getCustomerId(), command.getCode(),
                        command.getName(), command.getCredit()));
    }

    @CommandHandler
    public void handle(UpdateCustomerAccountCommand command) {
        Aggregate<CustomerAccountAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.update(command.getId(), command.getCode(), command.getName()));
    }

    @CommandHandler
    public void handle(IncreaseCustomerAccountCreditCommand command) {
        Aggregate<CustomerAccountAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.increaseCredit(command.getId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(DecreaseCustomerAccountCreditCommand command) {
        Aggregate<CustomerAccountAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.decreaseCredit(command.getId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(DeleteCustomerAccountCommand command) {
        Aggregate<CustomerAccountAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.delete(command.getId()));
    }
}
