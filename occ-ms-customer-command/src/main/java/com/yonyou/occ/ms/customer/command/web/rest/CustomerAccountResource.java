package com.yonyou.occ.ms.customer.command.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.customer.command.customeraccount.CreateCustomerAccountCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.DeleteCustomerAccountCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.IncreaseCustomerAccountCreditCommand;
import com.yonyou.occ.ms.customer.command.customeraccount.UpdateCustomerAccountCommand;
import com.yonyou.occ.ms.customer.command.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.customer.command.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.vo.CustomerAccountId;
import com.yonyou.occ.ms.customer.vo.CustomerId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CustomerAccount.
 */
@RestController
@RequestMapping("/api")
public class CustomerAccountResource {
    private final Logger log = LoggerFactory.getLogger(CustomerAccountResource.class);

    private static final String ENTITY_NAME = "customerAccount";

    private final CommandGateway commandGateway;

    public CustomerAccountResource(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * POST  /customer-accounts : Create a new customerAccount.
     *
     * @param customerAccountDTO the customerAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerAccountDTO, or with status
     * 400 (Bad Request) if the customerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-accounts")
    @Timed
    public ResponseEntity<CustomerAccountDTO> createCustomerAccount(
        @Valid @RequestBody CustomerAccountDTO customerAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerAccount : {}", customerAccountDTO);
        if (customerAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerAccount cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        String id = UUID.randomUUID().toString();
        CreateCustomerAccountCommand command = new CreateCustomerAccountCommand(new CustomerAccountId(id),
            new CustomerId(customerAccountDTO.getCustomerId()), customerAccountDTO.getCode(),
            customerAccountDTO.getName(), customerAccountDTO.getCredit());
        commandGateway.sendAndWait(command);

        return ResponseEntity.created(new URI("/api/customer-accounts/" + id)).headers(
            HeaderUtil.createEntityCreationAlert(ENTITY_NAME, id)).body(customerAccountDTO);
    }

    /**
     * PUT  /customer-accounts : Updates an existing customerAccount.
     *
     * @param customerAccountDTO the customerAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerAccountDTO,
     * or with status 400 (Bad Request) if the customerAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-accounts")
    @Timed
    public ResponseEntity<CustomerAccountDTO> updateCustomerAccount(
        @Valid @RequestBody CustomerAccountDTO customerAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerAccount : {}", customerAccountDTO);
        if (customerAccountDTO.getId() == null) {
            return createCustomerAccount(customerAccountDTO);
        }

        UpdateCustomerAccountCommand command = new UpdateCustomerAccountCommand(
            new CustomerAccountId(customerAccountDTO.getId()), customerAccountDTO.getCode(),
            customerAccountDTO.getName());
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerAccountDTO.getId()))
            .body(customerAccountDTO);
    }

    /**
     * PUT  /customer-accounts/:id/increase-credit : Increase the credit of an existing customerAccount.
     *
     * @param id the id of the customerAccountDTO to increase credit
     * @param quantity the quantity to increase credit
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/customer-accounts/{id}/increase-credit")
    @Timed
    public ResponseEntity<Void> increaseCustomerAccountCredit(@PathVariable String id,
        @RequestParam BigDecimal quantity) {
        log.debug("REST request to increase credit of CustomerAccount : {}", id);

        IncreaseCustomerAccountCreditCommand command = new IncreaseCustomerAccountCreditCommand(
            new CustomerAccountId(id), quantity);
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id)).build();
    }

    /**
     * DELETE  /customer-accounts/:id : delete the "id" customerAccount.
     *
     * @param id the id of the customerAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerAccount(@PathVariable String id) {
        log.debug("REST request to delete CustomerAccount : {}", id);

        DeleteCustomerAccountCommand command = new DeleteCustomerAccountCommand(new CustomerAccountId(id));
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
