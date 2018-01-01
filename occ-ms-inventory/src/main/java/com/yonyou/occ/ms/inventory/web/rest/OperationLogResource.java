package com.yonyou.occ.ms.inventory.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.inventory.domain.OperationLog;

import com.yonyou.occ.ms.inventory.repository.OperationLogRepository;
import com.yonyou.occ.ms.inventory.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.inventory.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.inventory.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.inventory.service.dto.OperationLogDTO;
import com.yonyou.occ.ms.inventory.service.mapper.OperationLogMapper;
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
 * REST controller for managing OperationLog.
 */
@RestController
@RequestMapping("/api")
public class OperationLogResource {

    private final Logger log = LoggerFactory.getLogger(OperationLogResource.class);

    private static final String ENTITY_NAME = "operationLog";

    private final OperationLogRepository operationLogRepository;

    private final OperationLogMapper operationLogMapper;

    public OperationLogResource(OperationLogRepository operationLogRepository, OperationLogMapper operationLogMapper) {
        this.operationLogRepository = operationLogRepository;
        this.operationLogMapper = operationLogMapper;
    }

    /**
     * POST  /operation-logs : Create a new operationLog.
     *
     * @param operationLogDTO the operationLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationLogDTO, or with status 400 (Bad Request) if the operationLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operation-logs")
    @Timed
    public ResponseEntity<OperationLogDTO> createOperationLog(@Valid @RequestBody OperationLogDTO operationLogDTO) throws URISyntaxException {
        log.debug("REST request to save OperationLog : {}", operationLogDTO);
        if (operationLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationLog operationLog = operationLogMapper.toEntity(operationLogDTO);
        operationLog = operationLogRepository.save(operationLog);
        OperationLogDTO result = operationLogMapper.toDto(operationLog);
        return ResponseEntity.created(new URI("/api/operation-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operation-logs : Updates an existing operationLog.
     *
     * @param operationLogDTO the operationLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationLogDTO,
     * or with status 400 (Bad Request) if the operationLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operation-logs")
    @Timed
    public ResponseEntity<OperationLogDTO> updateOperationLog(@Valid @RequestBody OperationLogDTO operationLogDTO) throws URISyntaxException {
        log.debug("REST request to update OperationLog : {}", operationLogDTO);
        if (operationLogDTO.getId() == null) {
            return createOperationLog(operationLogDTO);
        }
        OperationLog operationLog = operationLogMapper.toEntity(operationLogDTO);
        operationLog = operationLogRepository.save(operationLog);
        OperationLogDTO result = operationLogMapper.toDto(operationLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operation-logs : get all the operationLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operationLogs in body
     */
    @GetMapping("/operation-logs")
    @Timed
    public ResponseEntity<List<OperationLogDTO>> getAllOperationLogs(Pageable pageable) {
        log.debug("REST request to get a page of OperationLogs");
        Page<OperationLog> page = operationLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operation-logs");
        return new ResponseEntity<>(operationLogMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /operation-logs/:id : get the "id" operationLog.
     *
     * @param id the id of the operationLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operation-logs/{id}")
    @Timed
    public ResponseEntity<OperationLogDTO> getOperationLog(@PathVariable Long id) {
        log.debug("REST request to get OperationLog : {}", id);
        OperationLog operationLog = operationLogRepository.findOne(id);
        OperationLogDTO operationLogDTO = operationLogMapper.toDto(operationLog);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationLogDTO));
    }

    /**
     * DELETE  /operation-logs/:id : delete the "id" operationLog.
     *
     * @param id the id of the operationLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operation-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperationLog(@PathVariable Long id) {
        log.debug("REST request to delete OperationLog : {}", id);
        operationLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
