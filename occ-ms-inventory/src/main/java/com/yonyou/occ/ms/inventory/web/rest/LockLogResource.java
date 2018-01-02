package com.yonyou.occ.ms.inventory.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.inventory.domain.LockLog;
import com.yonyou.occ.ms.inventory.repository.LockLogRepository;
import com.yonyou.occ.ms.inventory.service.dto.LockLogDTO;
import com.yonyou.occ.ms.inventory.service.mapper.LockLogMapper;
import com.yonyou.occ.ms.inventory.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.inventory.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.inventory.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing LockLog.
 */
@RestController
@RequestMapping("/api")
public class LockLogResource {

    private final Logger log = LoggerFactory.getLogger(LockLogResource.class);

    private static final String ENTITY_NAME = "lockLog";

    private final LockLogRepository lockLogRepository;

    private final LockLogMapper lockLogMapper;

    public LockLogResource(LockLogRepository lockLogRepository, LockLogMapper lockLogMapper) {
        this.lockLogRepository = lockLogRepository;
        this.lockLogMapper = lockLogMapper;
    }

    /**
     * POST  /lock-logs : Create a new lockLog.
     *
     * @param lockLogDTO the lockLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lockLogDTO, or with status 400 (Bad Request) if the lockLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lock-logs")
    @Timed
    public ResponseEntity<LockLogDTO> createLockLog(@Valid @RequestBody LockLogDTO lockLogDTO) throws URISyntaxException {
        log.debug("REST request to save LockLog : {}", lockLogDTO);
        if (lockLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new lockLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LockLog lockLog = lockLogMapper.toEntity(lockLogDTO);
        lockLog.setId(UUID.randomUUID().toString());
        lockLog = lockLogRepository.save(lockLog);
        LockLogDTO result = lockLogMapper.toDto(lockLog);
        return ResponseEntity.created(new URI("/api/lock-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lock-logs : Updates an existing lockLog.
     *
     * @param lockLogDTO the lockLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lockLogDTO,
     * or with status 400 (Bad Request) if the lockLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the lockLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lock-logs")
    @Timed
    public ResponseEntity<LockLogDTO> updateLockLog(@Valid @RequestBody LockLogDTO lockLogDTO) throws URISyntaxException {
        log.debug("REST request to update LockLog : {}", lockLogDTO);
        if (lockLogDTO.getId() == null) {
            return createLockLog(lockLogDTO);
        }
        LockLog lockLog = lockLogMapper.toEntity(lockLogDTO);
        lockLog = lockLogRepository.save(lockLog);
        LockLogDTO result = lockLogMapper.toDto(lockLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lockLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lock-logs : get all the lockLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lockLogs in body
     */
    @GetMapping("/lock-logs")
    @Timed
    public ResponseEntity<List<LockLogDTO>> getAllLockLogs(Pageable pageable) {
        log.debug("REST request to get a page of LockLogs");
        Page<LockLog> page = lockLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lock-logs");
        return new ResponseEntity<>(lockLogMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /lock-logs/:id : get the "id" lockLog.
     *
     * @param id the id of the lockLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lockLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lock-logs/{id}")
    @Timed
    public ResponseEntity<LockLogDTO> getLockLog(@PathVariable String id) {
        log.debug("REST request to get LockLog : {}", id);
        LockLog lockLog = lockLogRepository.findOne(id);
        LockLogDTO lockLogDTO = lockLogMapper.toDto(lockLog);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lockLogDTO));
    }

    /**
     * DELETE  /lock-logs/:id : delete the "id" lockLog.
     *
     * @param id the id of the lockLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lock-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLockLog(@PathVariable String id) {
        log.debug("REST request to delete LockLog : {}", id);
        lockLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
