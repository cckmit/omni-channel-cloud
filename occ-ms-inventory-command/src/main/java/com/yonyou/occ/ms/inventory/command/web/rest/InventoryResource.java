package com.yonyou.occ.ms.inventory.command.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.common.domain.vo.inventory.InventoryId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import com.yonyou.occ.ms.inventory.command.inventory.CreateInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.IncreaseInventoryCommand;
import com.yonyou.occ.ms.inventory.command.web.rest.util.HeaderUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Inventory.
 */
@RestController
@RequestMapping("/api")
public class InventoryResource {
    private final Logger log = LoggerFactory.getLogger(InventoryResource.class);

    private static final String ENTITY_NAME = "inventory";

    private final CommandGateway commandGateway;

    public InventoryResource(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * POST  /inventories : Create a new inventory.
     *
     * @param productId ID of the product
     * @param quantity Quantity of the product
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryDTO, or with status 400
     * (Bad Request) if the inventory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventories")
    @Timed
    public ResponseEntity<Void> createInventory(@RequestParam String productId, @RequestParam BigDecimal quantity)
        throws URISyntaxException {
        log.debug("REST request to create Inventory of product : {}", productId);

        String id = UUID.randomUUID().toString();
        CreateInventoryCommand command = new CreateInventoryCommand(new InventoryId(id), new ProductId(productId),
            quantity);
        commandGateway.sendAndWait(command);

        return ResponseEntity.created(new URI("/api/inventories/" + id)).headers(
            HeaderUtil.createEntityCreationAlert(ENTITY_NAME, id)).build();
    }

    /**
     * PUT  /inventories/:id/increase : Increase the quantity of an existing inventory.
     *
     * @param id the id of the inventoryDTO to increase quantity
     * @param quantity the quantity to increase
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/inventories/{id}/increase")
    @Timed
    public ResponseEntity<Void> increaseInventory(@PathVariable String id, @RequestParam BigDecimal quantity) {
        log.debug("REST request to increase quantity of Inventory : {}", id);

        IncreaseInventoryCommand command = new IncreaseInventoryCommand(new InventoryId(id), quantity);
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id)).build();
    }
}
