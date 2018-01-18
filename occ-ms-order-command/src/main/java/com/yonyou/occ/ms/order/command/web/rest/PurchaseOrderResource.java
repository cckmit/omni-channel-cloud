package com.yonyou.occ.ms.order.command.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerId;
import com.yonyou.occ.ms.common.domain.vo.order.PoTypeId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import com.yonyou.occ.ms.order.command.po.CreatePurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.DeletePurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.StartPayPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.po.SubmitPurchaseOrderCommand;
import com.yonyou.occ.ms.order.command.web.rest.dto.CreatePurchaseOrderRequestDTO;
import com.yonyou.occ.ms.order.command.web.rest.util.HeaderUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing PurchaseOrder.
 *
 * @author WangRui
 * @date 2018-01-11 09:46:26
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
public class PurchaseOrderResource {
    private final Logger log = LoggerFactory.getLogger(PurchaseOrderResource.class);

    private static final String ENTITY_NAME = "purchaseOrder";

    private final CommandGateway commandGateway;

    public PurchaseOrderResource(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * POST  /purchase-orders : Create a new purchaseOrder.
     *
     * @param requestDTO the request DTO to create a purchaseOrder
     * @return the ResponseEntity with status 201 (Created)
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-orders")
    @Timed
    public ResponseEntity<Void> createPurchaseOrder(@Valid @RequestBody CreatePurchaseOrderRequestDTO requestDTO)
        throws URISyntaxException {
        log.debug("REST request to create a purchaseOrder : {}", requestDTO);

        String id = UUID.randomUUID().toString();
        String code = id.replaceAll("-", "");
        Map<ProductId, BigDecimal> products = new HashMap<>(8);
        requestDTO.getProducts().forEach((productId, quantity) -> products.put(new ProductId(productId), quantity));
        CreatePurchaseOrderCommand command = new CreatePurchaseOrderCommand(new PurchaseOrderId(id), code,
            new PoTypeId(requestDTO.getPoTypeId()), new CustomerId(requestDTO.getCustomerId()),
            new CustomerAccountId(requestDTO.getCustomerAccountId()), products);
        commandGateway.sendAndWait(command);

        return ResponseEntity.created(new URI("/api/purchase-orders/" + id)).headers(
            HeaderUtil.createEntityCreationAlert(ENTITY_NAME, id)).build();
    }

    /**
     * PUT  /purchase-orders/:id/pay : Pay an existing purchaseOrder.
     *
     * @param id the id of the purchaseOrderDTO to pay
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/purchase-orders/{id}/pay")
    @Timed
    public ResponseEntity<Void> payPurchaseOrder(@PathVariable String id) {
        log.debug("REST request to pay a purchaseOrder : {}", id);

        StartPayPurchaseOrderCommand command = new StartPayPurchaseOrderCommand(new PurchaseOrderId(id));
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id)).build();
    }

    /**
     * PUT  /purchase-orders/:id/submit : Submit an existing purchaseOrder.
     *
     * @param id the id of the purchaseOrderDTO to submit
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/purchase-orders/{id}/submit")
    @Timed
    public ResponseEntity<Void> submitPurchaseOrder(@PathVariable String id) {
        log.debug("REST request to pay a purchaseOrder : {}", id);

        SubmitPurchaseOrderCommand command = new SubmitPurchaseOrderCommand(new PurchaseOrderId(id));
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id)).build();
    }

    /**
     * DELETE  /purchase-orders/:id : delete the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable String id) {
        log.debug("REST request to delete PurchaseOrder : {}", id);

        DeletePurchaseOrderCommand command = new DeletePurchaseOrderCommand(new PurchaseOrderId(id));
        commandGateway.sendAndWait(command);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
