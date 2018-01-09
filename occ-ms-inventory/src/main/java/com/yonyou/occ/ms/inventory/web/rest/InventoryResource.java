package com.yonyou.occ.ms.inventory.web.rest;

import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.inventory.service.InventoryService;
import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;
import com.yonyou.occ.ms.inventory.web.rest.api.InventoryRestApi;
import com.yonyou.occ.ms.inventory.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Inventory.
 */
@RestController
//@RequestMapping("/api")
public class InventoryResource implements InventoryRestApi {
    private final Logger log = LoggerFactory.getLogger(InventoryResource.class);

    private static final String ENTITY_NAME = "inventory";

    private final InventoryService inventoryService;

    public InventoryResource(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * GET  /inventories : get all the inventories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inventories in body
     */
    @Timed
    @Override
    public ResponseEntity<List<InventoryDTO>> getAllInventories(Pageable pageable) {
        log.debug("REST request to get a page of Inventories");
        Page<InventoryDTO> page = inventoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventories/:id : get the "id" inventory.
     *
     * @param id the id of the inventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryDTO, or with status 404 (Not Found)
     */
    @Timed
    @Override
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable String id) {
        log.debug("REST request to get Inventory : {}", id);
        InventoryDTO inventoryDTO = inventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventoryDTO));
    }
}
