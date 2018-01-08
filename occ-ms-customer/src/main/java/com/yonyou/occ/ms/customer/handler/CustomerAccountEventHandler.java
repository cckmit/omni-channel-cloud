package com.yonyou.occ.ms.customer.handler;

import com.yonyou.occ.ms.customer.domain.Customer;
import com.yonyou.occ.ms.customer.domain.CustomerAccount;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreatedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditDecreasedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountCreditIncreasedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountDeletedEvent;
import com.yonyou.occ.ms.customer.event.customeraccount.CustomerAccountUpdatedEvent;
import com.yonyou.occ.ms.customer.repository.CustomerAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * CustomerAccount event handler class. CustomerAccount events are handled here.
 * For instance, using JPA Repository to save entity.
 *
 * @author WangRui
 * @date 2018-01-05 11:10:31
 */
@Slf4j
@Component
public class CustomerAccountEventHandler {
    private final CustomerAccountRepository customerAccountRepository;

    public CustomerAccountEventHandler(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @EventHandler
    public void handle(CustomerAccountCreatedEvent event) {
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setId(event.getId().getId());
        Customer customer = new Customer();
        customer.setId(event.getCustomerId().getId());
        customerAccount.setCustomer(customer);
        customerAccount.setCode(event.getCode());
        customerAccount.setName(event.getName());
        customerAccount.setCredit(event.getCredit());
        customerAccountRepository.save(customerAccount);

        log.info("CustomerAccount {} is created.", customerAccount);
    }

    @EventHandler
    public void handle(CustomerAccountUpdatedEvent event) {
        CustomerAccount customerAccount = customerAccountRepository.findOne(event.getId().getId());
        customerAccount.setCode(event.getCode());
        customerAccount.setName(event.getName());
        customerAccountRepository.save(customerAccount);

        log.info("CustomerAccount {} is updated.", customerAccount);
    }

    @EventHandler
    public void handle(CustomerAccountCreditIncreasedEvent event) {
        CustomerAccount customerAccount = customerAccountRepository.findOne(event.getId().getId());
        customerAccount.setCredit(customerAccount.getCredit().add(event.getAmount()));
        customerAccountRepository.save(customerAccount);

        log.info("Credit of CustomerAccount {} is increased.", customerAccount);
    }

    @EventHandler
    public void handle(CustomerAccountCreditDecreasedEvent event) {
        CustomerAccount customerAccount = customerAccountRepository.findOne(event.getId().getId());
        customerAccount.setCredit(customerAccount.getCredit().subtract(event.getAmount()));
        customerAccountRepository.save(customerAccount);

        log.info("Credit of CustomerAccount {} is decreased.", customerAccount);
    }

    @EventHandler
    public void handle(CustomerAccountDeletedEvent event) {
        CustomerAccount customerAccount = customerAccountRepository.findOne(event.getId().getId());
        customerAccount.setDr(1);
        customerAccountRepository.save(customerAccount);

        log.info("CustomerAccount {} is deleted.", customerAccount);
    }
}
