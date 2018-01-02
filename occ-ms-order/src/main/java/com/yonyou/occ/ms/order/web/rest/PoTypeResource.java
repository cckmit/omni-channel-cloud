package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.service.PoTypeService;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.PoTypeDTO;
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
 * REST controller for managing PoType.
 */
@RestController
@RequestMapping("/api")
public class PoTypeResource {

    private final Logger log = LoggerFactory.getLogger(PoTypeResource.class);

    private static final String ENTITY_NAME = "poType";

    private final PoTypeService poTypeService;

    public PoTypeResource(PoTypeService poTypeService) {
        this.poTypeService = poTypeService;
    }

    /**
     * POST  /po-types : Create a new poType.
     *
     * @param poTypeDTO the poTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poTypeDTO, or with status 400 (Bad Request) if the poType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-types")
    @Timed
    public ResponseEntity<PoTypeDTO> createPoType(@Valid @RequestBody PoTypeDTO poTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PoType : {}", poTypeDTO);
        if (poTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new poType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoTypeDTO result = poTypeService.save(poTypeDTO);
        return ResponseEntity.created(new URI("/api/po-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-types : Updates an existing poType.
     *
     * @param poTypeDTO the poTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poTypeDTO,
     * or with status 400 (Bad Request) if the poTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the poTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-types")
    @Timed
    public ResponseEntity<PoTypeDTO> updatePoType(@Valid @RequestBody PoTypeDTO poTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PoType : {}", poTypeDTO);
        if (poTypeDTO.getId() == null) {
            return createPoType(poTypeDTO);
        }
        PoTypeDTO result = poTypeService.save(poTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-types : get all the poTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of poTypes in body
     */
    @GetMapping("/po-types")
    @Timed
    public ResponseEntity<List<PoTypeDTO>> getAllPoTypes(Pageable pageable) {
        log.debug("REST request to get a page of PoTypes");
        Page<PoTypeDTO> page = poTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /po-types/:id : get the "id" poType.
     *
     * @param id the id of the poTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/po-types/{id}")
    @Timed
    public ResponseEntity<PoTypeDTO> getPoType(@PathVariable String id) {
        log.debug("REST request to get PoType : {}", id);
        PoTypeDTO poTypeDTO = poTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(poTypeDTO));
    }

    /**
     * DELETE  /po-types/:id : delete the "id" poType.
     *
     * @param id the id of the poTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePoType(@PathVariable String id) {
        log.debug("REST request to delete PoType : {}", id);
        poTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
