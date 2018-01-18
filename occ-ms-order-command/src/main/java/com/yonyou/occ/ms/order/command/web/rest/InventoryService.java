package com.yonyou.occ.ms.order.command.web.rest;


import com.yonyou.occ.ms.inventory.web.rest.api.InventoryRestApi;
import com.yonyou.occ.ms.order.command.client.AuthorizedFeignClient;

/**
 * Feign client for inventory REST API.
 *
 * @author WangRui
 * @date 2018-01-17 09:35:23
 */
@AuthorizedFeignClient(name = "occmsinventory")
public interface InventoryService extends InventoryRestApi {
}
