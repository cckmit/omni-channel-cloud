package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.service.PoStateService;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.PoStateDTO;
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
 * REST controller for managing PoState.
 */
@RestController
@RequestMapping("/api")
public class PoStateResource {

    private final Logger log = LoggerFactory.getLogger(PoStateResource.class);

    private static final String ENTITY_NAME = "poState";

    private final PoStateService poStateService;

    public PoStateResource(PoStateService poStateService) {
        this.poStateService = poStateService;
    }

    /**
     * POST  /po-states : Create a new poState.
     *
     * @param poStateDTO the poStateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poStateDTO, or with status 400 (Bad Request) if the poState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-states")
    @Timed
    public ResponseEntity<PoStateDTO> createPoState(@Valid @RequestBody PoStateDTO poStateDTO) throws URISyntaxException {
        log.debug("REST request to save PoState : {}", poStateDTO);
        if (poStateDTO.getId() != null) {
            throw new BadRequestAlertException("A new poState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoStateDTO result = poStateService.save(poStateDTO);
        return ResponseEntity.created(new URI("/api/po-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-states : Updates an existing poState.
     *
     * @param poStateDTO the poStateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poStateDTO,
     * or with status 400 (Bad Request) if the poStateDTO is not valid,
     * or with status 500 (Internal Server Error) if the poStateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-states")
    @Timed
    public ResponseEntity<PoStateDTO> updatePoState(@Valid @RequestBody PoStateDTO poStateDTO) throws URISyntaxException {
        log.debug("REST request to update PoState : {}", poStateDTO);
        if (poStateDTO.getId() == null) {
            return createPoState(poStateDTO);
        }
        PoStateDTO result = poStateService.save(poStateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poStateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-states : get all the poStates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of poStates in body
     */
    @GetMapping("/po-states")
    @Timed
    public ResponseEntity<List<PoStateDTO>> getAllPoStates(Pageable pageable) {
        log.debug("REST request to get a page of PoStates");
        Page<PoStateDTO> page = poStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /po-states/:id : get the "id" poState.
     *
     * @param id the id of the poStateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poStateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/po-states/{id}")
    @Timed
    public ResponseEntity<PoStateDTO> getPoState(@PathVariable String id) {
        log.debug("REST request to get PoState : {}", id);
        PoStateDTO poStateDTO = poStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(poStateDTO));
    }

    /**
     * DELETE  /po-states/:id : delete the "id" poState.
     *
     * @param id the id of the poStateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-states/{id}")
    @Timed
    public ResponseEntity<Void> deletePoState(@PathVariable String id) {
        log.debug("REST request to delete PoState : {}", id);
        poStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
