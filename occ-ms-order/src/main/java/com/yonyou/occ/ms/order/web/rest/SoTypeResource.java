package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.service.SoTypeService;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.SoTypeDTO;
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
 * REST controller for managing SoType.
 */
@RestController
@RequestMapping("/api")
public class SoTypeResource {

    private final Logger log = LoggerFactory.getLogger(SoTypeResource.class);

    private static final String ENTITY_NAME = "soType";

    private final SoTypeService soTypeService;

    public SoTypeResource(SoTypeService soTypeService) {
        this.soTypeService = soTypeService;
    }

    /**
     * POST  /so-types : Create a new soType.
     *
     * @param soTypeDTO the soTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soTypeDTO, or with status 400 (Bad Request) if the soType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/so-types")
    @Timed
    public ResponseEntity<SoTypeDTO> createSoType(@Valid @RequestBody SoTypeDTO soTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SoType : {}", soTypeDTO);
        if (soTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new soType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoTypeDTO result = soTypeService.save(soTypeDTO);
        return ResponseEntity.created(new URI("/api/so-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /so-types : Updates an existing soType.
     *
     * @param soTypeDTO the soTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soTypeDTO,
     * or with status 400 (Bad Request) if the soTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the soTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/so-types")
    @Timed
    public ResponseEntity<SoTypeDTO> updateSoType(@Valid @RequestBody SoTypeDTO soTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SoType : {}", soTypeDTO);
        if (soTypeDTO.getId() == null) {
            return createSoType(soTypeDTO);
        }
        SoTypeDTO result = soTypeService.save(soTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, soTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /so-types : get all the soTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of soTypes in body
     */
    @GetMapping("/so-types")
    @Timed
    public ResponseEntity<List<SoTypeDTO>> getAllSoTypes(Pageable pageable) {
        log.debug("REST request to get a page of SoTypes");
        Page<SoTypeDTO> page = soTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/so-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /so-types/:id : get the "id" soType.
     *
     * @param id the id of the soTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/so-types/{id}")
    @Timed
    public ResponseEntity<SoTypeDTO> getSoType(@PathVariable Long id) {
        log.debug("REST request to get SoType : {}", id);
        SoTypeDTO soTypeDTO = soTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(soTypeDTO));
    }

    /**
     * DELETE  /so-types/:id : delete the "id" soType.
     *
     * @param id the id of the soTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/so-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSoType(@PathVariable Long id) {
        log.debug("REST request to delete SoType : {}", id);
        soTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
