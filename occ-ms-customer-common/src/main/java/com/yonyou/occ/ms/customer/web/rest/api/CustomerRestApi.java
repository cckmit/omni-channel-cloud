package com.yonyou.occ.ms.customer.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.customer.service.dto.CustomerDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The REST API for customer.
 *
 * @author WangRui
 * @date 2018-01-05 09:22:42
 */
@RequestMapping("/api")
public interface CustomerRestApi {
    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    ResponseEntity<List<CustomerDTO>> getAllCustomers(Pageable pageable);

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id);
}
