package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.service.SoStateService;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.SoStateDTO;
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
 * REST controller for managing SoState.
 */
@RestController
@RequestMapping("/api")
public class SoStateResource {

    private final Logger log = LoggerFactory.getLogger(SoStateResource.class);

    private static final String ENTITY_NAME = "soState";

    private final SoStateService soStateService;

    public SoStateResource(SoStateService soStateService) {
        this.soStateService = soStateService;
    }

    /**
     * POST  /so-states : Create a new soState.
     *
     * @param soStateDTO the soStateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soStateDTO, or with status 400 (Bad Request) if the soState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/so-states")
    @Timed
    public ResponseEntity<SoStateDTO> createSoState(@Valid @RequestBody SoStateDTO soStateDTO) throws URISyntaxException {
        log.debug("REST request to save SoState : {}", soStateDTO);
        if (soStateDTO.getId() != null) {
            throw new BadRequestAlertException("A new soState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoStateDTO result = soStateService.save(soStateDTO);
        return ResponseEntity.created(new URI("/api/so-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /so-states : Updates an existing soState.
     *
     * @param soStateDTO the soStateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soStateDTO,
     * or with status 400 (Bad Request) if the soStateDTO is not valid,
     * or with status 500 (Internal Server Error) if the soStateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/so-states")
    @Timed
    public ResponseEntity<SoStateDTO> updateSoState(@Valid @RequestBody SoStateDTO soStateDTO) throws URISyntaxException {
        log.debug("REST request to update SoState : {}", soStateDTO);
        if (soStateDTO.getId() == null) {
            return createSoState(soStateDTO);
        }
        SoStateDTO result = soStateService.save(soStateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, soStateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /so-states : get all the soStates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of soStates in body
     */
    @GetMapping("/so-states")
    @Timed
    public ResponseEntity<List<SoStateDTO>> getAllSoStates(Pageable pageable) {
        log.debug("REST request to get a page of SoStates");
        Page<SoStateDTO> page = soStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/so-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /so-states/:id : get the "id" soState.
     *
     * @param id the id of the soStateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soStateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/so-states/{id}")
    @Timed
    public ResponseEntity<SoStateDTO> getSoState(@PathVariable String id) {
        log.debug("REST request to get SoState : {}", id);
        SoStateDTO soStateDTO = soStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(soStateDTO));
    }

    /**
     * DELETE  /so-states/:id : delete the "id" soState.
     *
     * @param id the id of the soStateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/so-states/{id}")
    @Timed
    public ResponseEntity<Void> deleteSoState(@PathVariable String id) {
        log.debug("REST request to delete SoState : {}", id);
        soStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
