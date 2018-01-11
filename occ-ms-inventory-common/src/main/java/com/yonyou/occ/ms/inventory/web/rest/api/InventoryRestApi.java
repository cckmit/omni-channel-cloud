package com.yonyou.occ.ms.inventory.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The REST API for inventory.
 *
 * @author WangRui
 * @date 2018-01-08 11:31:34
 */
@RequestMapping("/api")
public interface InventoryRestApi {
    /**
     * GET  /inventories : get all the inventories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inventories in body
     */
    @GetMapping("/inventories")
    ResponseEntity<List<InventoryDTO>> getAllInventories(Pageable pageable);

    /**
     * GET  /inventories/:id : get the "id" inventory.
     *
     * @param id the id of the inventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inventories/{id}")
    ResponseEntity<InventoryDTO> getInventory(@PathVariable("id") String id);
}
