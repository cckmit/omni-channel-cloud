package com.yonyou.occ.ms.customer.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The REST API for customer account.
 *
 * @author WangRui
 * @date 2018-01-05 09:22:42
 */
@RequestMapping("/api")
public interface CustomerAccountRestApi {
    /**
     * GET  /customer-accounts?customerId={customerId} : get customerAccounts by customer.
     *
     * @param pageable the pagination information
     * @param customerId the id of the customer
     * @return the ResponseEntity with status 200 (OK) and the list of customerAccounts in body
     */
    @GetMapping("/customers")
    ResponseEntity<List<CustomerAccountDTO>> getCustomerAccountsByCustomer(Pageable pageable,
            @RequestParam String customerId);

    /**
     * GET  /customer-accounts/:id : get the "id" customerAccount.
     *
     * @param id the id of the customerAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAccountDTO, or with status 404 (Not
     * Found)
     */
    @GetMapping("/customer-accounts/{id}")
    ResponseEntity<CustomerAccountDTO> getCustomerAccount(@PathVariable String id);
}
