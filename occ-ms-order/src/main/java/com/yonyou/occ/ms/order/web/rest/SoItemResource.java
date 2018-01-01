package com.yonyou.occ.ms.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.domain.SoItem;

import com.yonyou.occ.ms.order.repository.SoItemRepository;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
import com.yonyou.occ.ms.order.service.dto.SoItemDTO;
import com.yonyou.occ.ms.order.service.mapper.SoItemMapper;
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
 * REST controller for managing SoItem.
 */
@RestController
@RequestMapping("/api")
public class SoItemResource {

    private final Logger log = LoggerFactory.getLogger(SoItemResource.class);

    private static final String ENTITY_NAME = "soItem";

    private final SoItemRepository soItemRepository;

    private final SoItemMapper soItemMapper;

    public SoItemResource(SoItemRepository soItemRepository, SoItemMapper soItemMapper) {
        this.soItemRepository = soItemRepository;
        this.soItemMapper = soItemMapper;
    }

    /**
     * POST  /so-items : Create a new soItem.
     *
     * @param soItemDTO the soItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soItemDTO, or with status 400 (Bad Request) if the soItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/so-items")
    @Timed
    public ResponseEntity<SoItemDTO> createSoItem(@Valid @RequestBody SoItemDTO soItemDTO) throws URISyntaxException {
        log.debug("REST request to save SoItem : {}", soItemDTO);
        if (soItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new soItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoItem soItem = soItemMapper.toEntity(soItemDTO);
        soItem = soItemRepository.save(soItem);
        SoItemDTO result = soItemMapper.toDto(soItem);
        return ResponseEntity.created(new URI("/api/so-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /so-items : Updates an existing soItem.
     *
     * @param soItemDTO the soItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soItemDTO,
     * or with status 400 (Bad Request) if the soItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the soItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/so-items")
    @Timed
    public ResponseEntity<SoItemDTO> updateSoItem(@Valid @RequestBody SoItemDTO soItemDTO) throws URISyntaxException {
        log.debug("REST request to update SoItem : {}", soItemDTO);
        if (soItemDTO.getId() == null) {
            return createSoItem(soItemDTO);
        }
        SoItem soItem = soItemMapper.toEntity(soItemDTO);
        soItem = soItemRepository.save(soItem);
        SoItemDTO result = soItemMapper.toDto(soItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, soItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /so-items : get all the soItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of soItems in body
     */
    @GetMapping("/so-items")
    @Timed
    public ResponseEntity<List<SoItemDTO>> getAllSoItems(Pageable pageable) {
        log.debug("REST request to get a page of SoItems");
        Page<SoItem> page = soItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/so-items");
        return new ResponseEntity<>(soItemMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /so-items/:id : get the "id" soItem.
     *
     * @param id the id of the soItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/so-items/{id}")
    @Timed
    public ResponseEntity<SoItemDTO> getSoItem(@PathVariable Long id) {
        log.debug("REST request to get SoItem : {}", id);
        SoItem soItem = soItemRepository.findOne(id);
        SoItemDTO soItemDTO = soItemMapper.toDto(soItem);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(soItemDTO));
    }

    /**
     * DELETE  /so-items/:id : delete the "id" soItem.
     *
     * @param id the id of the soItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/so-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSoItem(@PathVariable Long id) {
        log.debug("REST request to delete SoItem : {}", id);
        soItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
