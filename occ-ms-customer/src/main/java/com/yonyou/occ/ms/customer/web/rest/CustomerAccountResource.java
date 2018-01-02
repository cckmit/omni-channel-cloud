package com.yonyou.occ.ms.customer.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.customer.service.CustomerAccountService;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.customer.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.customer.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CustomerAccount.
 */
@RestController
@RequestMapping("/api")
public class CustomerAccountResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAccountResource.class);

    private static final String ENTITY_NAME = "customerAccount";

    private final CustomerAccountService customerAccountService;

    public CustomerAccountResource(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    /**
     * POST  /customer-accounts : Create a new customerAccount.
     *
     * @param customerAccountDTO the customerAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerAccountDTO, or with status 400 (Bad Request) if the customerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-accounts")
    @Timed
    public ResponseEntity<CustomerAccountDTO> createCustomerAccount(@Valid @RequestBody CustomerAccountDTO customerAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerAccount : {}", customerAccountDTO);
        if (customerAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerAccountDTO result = customerAccountService.save(customerAccountDTO);
        return ResponseEntity.created(new URI("/api/customer-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<CustomerAccountDTO> updateCustomerAccount(@Valid @RequestBody CustomerAccountDTO customerAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerAccount : {}", customerAccountDTO);
        if (customerAccountDTO.getId() == null) {
            return createCustomerAccount(customerAccountDTO);
        }
        CustomerAccountDTO result = customerAccountService.save(customerAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-accounts : get all the customerAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerAccounts in body
     */
    @GetMapping("/customer-accounts")
    @Timed
    public ResponseEntity<List<CustomerAccountDTO>> getAllCustomerAccounts(Pageable pageable) {
        log.debug("REST request to get a page of CustomerAccounts");
        Page<CustomerAccountDTO> page = customerAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-accounts/:id : get the "id" customerAccount.
     *
     * @param id the id of the customerAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-accounts/{id}")
    @Timed
    public ResponseEntity<CustomerAccountDTO> getCustomerAccount(@PathVariable String id) {
        log.debug("REST request to get CustomerAccount : {}", id);
        CustomerAccountDTO customerAccountDTO = customerAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerAccountDTO));
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
        customerAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
