package com.yonyou.occ.ms.customer.web.rest;

import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.customer.service.CustomerAccountService;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.web.rest.api.CustomerAccountRestApi;
import com.yonyou.occ.ms.customer.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CustomerAccount.
 */
@RestController
//@RequestMapping("/api")
public class CustomerAccountResource implements CustomerAccountRestApi {
    private final Logger log = LoggerFactory.getLogger(CustomerAccountResource.class);

    private final CustomerAccountService customerAccountService;

    public CustomerAccountResource(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    /**
     * GET  /customer-accounts?customerId={customerId} : get customerAccounts by customer.
     *
     * @param pageable the pagination information
     * @param customerId the id of the customer
     * @return the ResponseEntity with status 200 (OK) and the list of customerAccounts in body
     */
    @Timed
    @Override
    public ResponseEntity<List<CustomerAccountDTO>> getCustomerAccountsByCustomer(Pageable pageable,
        @RequestParam String customerId) {
        log.debug("REST request to get CustomerAccounts by customer");
        Page<CustomerAccountDTO> page = customerAccountService.findByCustomerId(pageable, customerId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
            "/api/customer-accounts?customerId=" + customerId);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-accounts/:id : get the "id" customerAccount.
     *
     * @param id the id of the customerAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAccountDTO, or with status 404 (Not
     * Found)
     */
    @Timed
    @Override
    public ResponseEntity<CustomerAccountDTO> getCustomerAccount(@PathVariable String id) {
        log.debug("REST request to get CustomerAccount : {}", id);
        CustomerAccountDTO customerAccountDTO = customerAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerAccountDTO));
    }
}
