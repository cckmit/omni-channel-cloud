package com.yonyou.occ.ms.inventory.web.rest;

import com.yonyou.occ.ms.inventory.client.AuthorizedFeignClient;
import com.yonyou.occ.ms.product.web.rest.api.ProductRestApi;

/**
 * Feign client for product REST API.
 *
 * @author WangRui
 * @date 2018-01-08 09:40:47
 */
@AuthorizedFeignClient(name = "occmsproduct")
public interface ProductService extends ProductRestApi {
}
