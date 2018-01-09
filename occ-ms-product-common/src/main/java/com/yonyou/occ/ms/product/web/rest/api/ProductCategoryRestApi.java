package com.yonyou.occ.ms.product.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.product.service.dto.ProductCategoryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The REST API for product.
 *
 * @author WangRui
 * @date 2018-01-08 14:46:10
 */
public interface ProductCategoryRestApi {
    /**
     * GET  /product-categories : get all the productCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productCategories in body
     */
    @GetMapping("/product-categories")
    ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories(Pageable pageable);

    /**
     * GET  /product-categories/:id : get the "id" productCategory.
     *
     * @param id the id of the productCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategory(@PathVariable String id);
}
