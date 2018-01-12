package com.yonyou.occ.ms.order.command.web.rest;


import com.yonyou.occ.ms.customer.web.rest.api.CustomerAccountRestApi;
import com.yonyou.occ.ms.order.command.client.AuthorizedFeignClient;

/**
 * Feign client for customer account REST API.
 *
 * @author WangRui
 * @date 2018-01-11 08:38:16
 */
@AuthorizedFeignClient(name = "occmscustomer")
public interface CustomerAccountService extends CustomerAccountRestApi {
}
