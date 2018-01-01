package com.yonyou.occ.ms.inventory.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.inventory.service.OperationTypeService;
import com.yonyou.occ.ms.inventory.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.inventory.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.inventory.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.inventory.service.dto.OperationTypeDTO;
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
 * REST controller for managing OperationType.
 */
@RestController
@RequestMapping("/api")
public class OperationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OperationTypeResource.class);

    private static final String ENTITY_NAME = "operationType";

    private final OperationTypeService operationTypeService;

    public OperationTypeResource(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    /**
     * POST  /operation-types : Create a new operationType.
     *
     * @param operationTypeDTO the operationTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationTypeDTO, or with status 400 (Bad Request) if the operationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operation-types")
    @Timed
    public ResponseEntity<OperationTypeDTO> createOperationType(@Valid @RequestBody OperationTypeDTO operationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save OperationType : {}", operationTypeDTO);
        if (operationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationTypeDTO result = operationTypeService.save(operationTypeDTO);
        return ResponseEntity.created(new URI("/api/operation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operation-types : Updates an existing operationType.
     *
     * @param operationTypeDTO the operationTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationTypeDTO,
     * or with status 400 (Bad Request) if the operationTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operation-types")
    @Timed
    public ResponseEntity<OperationTypeDTO> updateOperationType(@Valid @RequestBody OperationTypeDTO operationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update OperationType : {}", operationTypeDTO);
        if (operationTypeDTO.getId() == null) {
            return createOperationType(operationTypeDTO);
        }
        OperationTypeDTO result = operationTypeService.save(operationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operation-types : get all the operationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operationTypes in body
     */
    @GetMapping("/operation-types")
    @Timed
    public ResponseEntity<List<OperationTypeDTO>> getAllOperationTypes(Pageable pageable) {
        log.debug("REST request to get a page of OperationTypes");
        Page<OperationTypeDTO> page = operationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operation-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operation-types/:id : get the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operation-types/{id}")
    @Timed
    public ResponseEntity<OperationTypeDTO> getOperationType(@PathVariable Long id) {
        log.debug("REST request to get OperationType : {}", id);
        OperationTypeDTO operationTypeDTO = operationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationTypeDTO));
    }

    /**
     * DELETE  /operation-types/:id : delete the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operation-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperationType(@PathVariable Long id) {
        log.debug("REST request to delete OperationType : {}", id);
        operationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
