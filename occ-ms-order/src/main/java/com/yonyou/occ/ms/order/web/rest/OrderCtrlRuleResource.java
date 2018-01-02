package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.service.OrderCtrlRuleService;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.OrderCtrlRuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderCtrlRule.
 */
@RestController
@RequestMapping("/api")
public class OrderCtrlRuleResource {

    private final Logger log = LoggerFactory.getLogger(OrderCtrlRuleResource.class);

    private static final String ENTITY_NAME = "orderCtrlRule";

    private final OrderCtrlRuleService orderCtrlRuleService;

    public OrderCtrlRuleResource(OrderCtrlRuleService orderCtrlRuleService) {
        this.orderCtrlRuleService = orderCtrlRuleService;
    }

    /**
     * POST  /order-ctrl-rules : Create a new orderCtrlRule.
     *
     * @param orderCtrlRuleDTO the orderCtrlRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderCtrlRuleDTO, or with status 400 (Bad Request) if the orderCtrlRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-ctrl-rules")
    @Timed
    public ResponseEntity<OrderCtrlRuleDTO> createOrderCtrlRule(@Valid @RequestBody OrderCtrlRuleDTO orderCtrlRuleDTO) throws URISyntaxException {
        log.debug("REST request to save OrderCtrlRule : {}", orderCtrlRuleDTO);
        if (orderCtrlRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderCtrlRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderCtrlRuleDTO result = orderCtrlRuleService.save(orderCtrlRuleDTO);
        return ResponseEntity.created(new URI("/api/order-ctrl-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-ctrl-rules : Updates an existing orderCtrlRule.
     *
     * @param orderCtrlRuleDTO the orderCtrlRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderCtrlRuleDTO,
     * or with status 400 (Bad Request) if the orderCtrlRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderCtrlRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-ctrl-rules")
    @Timed
    public ResponseEntity<OrderCtrlRuleDTO> updateOrderCtrlRule(@Valid @RequestBody OrderCtrlRuleDTO orderCtrlRuleDTO) throws URISyntaxException {
        log.debug("REST request to update OrderCtrlRule : {}", orderCtrlRuleDTO);
        if (orderCtrlRuleDTO.getId() == null) {
            return createOrderCtrlRule(orderCtrlRuleDTO);
        }
        OrderCtrlRuleDTO result = orderCtrlRuleService.save(orderCtrlRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderCtrlRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-ctrl-rules : get all the orderCtrlRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderCtrlRules in body
     */
    @GetMapping("/order-ctrl-rules")
    @Timed
    public ResponseEntity<List<OrderCtrlRuleDTO>> getAllOrderCtrlRules(Pageable pageable) {
        log.debug("REST request to get a page of OrderCtrlRules");
        Page<OrderCtrlRuleDTO> page = orderCtrlRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-ctrl-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-ctrl-rules/:id : get the "id" orderCtrlRule.
     *
     * @param id the id of the orderCtrlRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderCtrlRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-ctrl-rules/{id}")
    @Timed
    public ResponseEntity<OrderCtrlRuleDTO> getOrderCtrlRule(@PathVariable String id) {
        log.debug("REST request to get OrderCtrlRule : {}", id);
        OrderCtrlRuleDTO orderCtrlRuleDTO = orderCtrlRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderCtrlRuleDTO));
    }

    /**
     * DELETE  /order-ctrl-rules/:id : delete the "id" orderCtrlRule.
     *
     * @param id the id of the orderCtrlRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-ctrl-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderCtrlRule(@PathVariable String id) {
        log.debug("REST request to delete OrderCtrlRule : {}", id);
        orderCtrlRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
