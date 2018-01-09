package com.yonyou.occ.ms.product.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.product.service.dto.ProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The REST API for product.
 *
 * @author WangRui
 * @date 2018-01-08 14:46:10
 */
@RequestMapping("/api")
public interface ProductRestApi {
    /**
     * GET  /products : get all the products.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable);

    /**
     * GET  /products/:id : get the "id" product.
     *
     * @param id the id of the inventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    ResponseEntity<ProductDTO> getProduct(@PathVariable("id") String id);
}
