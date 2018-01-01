package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.domain.PoItem;

import com.yonyou.occ.ms.order.repository.PoItemRepository;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.PoItemDTO;
import com.yonyou.occ.ms.order.service.mapper.PoItemMapper;
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
 * REST controller for managing PoItem.
 */
@RestController
@RequestMapping("/api")
public class PoItemResource {

    private final Logger log = LoggerFactory.getLogger(PoItemResource.class);

    private static final String ENTITY_NAME = "poItem";

    private final PoItemRepository poItemRepository;

    private final PoItemMapper poItemMapper;

    public PoItemResource(PoItemRepository poItemRepository, PoItemMapper poItemMapper) {
        this.poItemRepository = poItemRepository;
        this.poItemMapper = poItemMapper;
    }

    /**
     * POST  /po-items : Create a new poItem.
     *
     * @param poItemDTO the poItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poItemDTO, or with status 400 (Bad Request) if the poItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-items")
    @Timed
    public ResponseEntity<PoItemDTO> createPoItem(@Valid @RequestBody PoItemDTO poItemDTO) throws URISyntaxException {
        log.debug("REST request to save PoItem : {}", poItemDTO);
        if (poItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new poItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoItem poItem = poItemMapper.toEntity(poItemDTO);
        poItem = poItemRepository.save(poItem);
        PoItemDTO result = poItemMapper.toDto(poItem);
        return ResponseEntity.created(new URI("/api/po-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-items : Updates an existing poItem.
     *
     * @param poItemDTO the poItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poItemDTO,
     * or with status 400 (Bad Request) if the poItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the poItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-items")
    @Timed
    public ResponseEntity<PoItemDTO> updatePoItem(@Valid @RequestBody PoItemDTO poItemDTO) throws URISyntaxException {
        log.debug("REST request to update PoItem : {}", poItemDTO);
        if (poItemDTO.getId() == null) {
            return createPoItem(poItemDTO);
        }
        PoItem poItem = poItemMapper.toEntity(poItemDTO);
        poItem = poItemRepository.save(poItem);
        PoItemDTO result = poItemMapper.toDto(poItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-items : get all the poItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of poItems in body
     */
    @GetMapping("/po-items")
    @Timed
    public ResponseEntity<List<PoItemDTO>> getAllPoItems(Pageable pageable) {
        log.debug("REST request to get a page of PoItems");
        Page<PoItem> page = poItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-items");
        return new ResponseEntity<>(poItemMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /po-items/:id : get the "id" poItem.
     *
     * @param id the id of the poItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/po-items/{id}")
    @Timed
    public ResponseEntity<PoItemDTO> getPoItem(@PathVariable Long id) {
        log.debug("REST request to get PoItem : {}", id);
        PoItem poItem = poItemRepository.findOne(id);
        PoItemDTO poItemDTO = poItemMapper.toDto(poItem);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(poItemDTO));
    }

    /**
     * DELETE  /po-items/:id : delete the "id" poItem.
     *
     * @param id the id of the poItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePoItem(@PathVariable Long id) {
        log.debug("REST request to delete PoItem : {}", id);
        poItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
