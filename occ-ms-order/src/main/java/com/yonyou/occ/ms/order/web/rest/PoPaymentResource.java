package com.yonyou.occ.ms.order.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.yonyou.occ.ms.order.domain.PoPayment;
import com.yonyou.occ.ms.order.repository.PoPaymentRepository;
import com.yonyou.occ.ms.order.service.dto.PoPaymentDTO;
import com.yonyou.occ.ms.order.service.mapper.PoPaymentMapper;
import com.yonyou.occ.ms.order.web.rest.errors.BadRequestAlertException;
import com.yonyou.occ.ms.order.web.rest.util.HeaderUtil;
import com.yonyou.occ.ms.order.web.rest.util.PaginationUtil;
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
 * REST controller for managing PoPayment.
 */
@RestController
@RequestMapping("/api")
public class PoPaymentResource {

    private final Logger log = LoggerFactory.getLogger(PoPaymentResource.class);

    private static final String ENTITY_NAME = "poPayment";

    private final PoPaymentRepository poPaymentRepository;

    private final PoPaymentMapper poPaymentMapper;

    public PoPaymentResource(PoPaymentRepository poPaymentRepository, PoPaymentMapper poPaymentMapper) {
        this.poPaymentRepository = poPaymentRepository;
        this.poPaymentMapper = poPaymentMapper;
    }

    /**
     * POST  /po-payments : Create a new poPayment.
     *
     * @param poPaymentDTO the poPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poPaymentDTO, or with status 400 (Bad Request) if the poPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-payments")
    @Timed
    public ResponseEntity<PoPaymentDTO> createPoPayment(@Valid @RequestBody PoPaymentDTO poPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save PoPayment : {}", poPaymentDTO);
        if (poPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new poPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoPayment poPayment = poPaymentMapper.toEntity(poPaymentDTO);
        poPayment.setId(UUID.randomUUID().toString());
        poPayment = poPaymentRepository.save(poPayment);
        PoPaymentDTO result = poPaymentMapper.toDto(poPayment);
        return ResponseEntity.created(new URI("/api/po-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-payments : Updates an existing poPayment.
     *
     * @param poPaymentDTO the poPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poPaymentDTO,
     * or with status 400 (Bad Request) if the poPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the poPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-payments")
    @Timed
    public ResponseEntity<PoPaymentDTO> updatePoPayment(@Valid @RequestBody PoPaymentDTO poPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update PoPayment : {}", poPaymentDTO);
        if (poPaymentDTO.getId() == null) {
            return createPoPayment(poPaymentDTO);
        }
        PoPayment poPayment = poPaymentMapper.toEntity(poPaymentDTO);
        poPayment = poPaymentRepository.save(poPayment);
        PoPaymentDTO result = poPaymentMapper.toDto(poPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-payments : get all the poPayments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of poPayments in body
     */
    @GetMapping("/po-payments")
    @Timed
    public ResponseEntity<List<PoPaymentDTO>> getAllPoPayments(Pageable pageable) {
        log.debug("REST request to get a page of PoPayments");
        Page<PoPayment> page = poPaymentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-payments");
        return new ResponseEntity<>(poPaymentMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /po-payments/:id : get the "id" poPayment.
     *
     * @param id the id of the poPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/po-payments/{id}")
    @Timed
    public ResponseEntity<PoPaymentDTO> getPoPayment(@PathVariable String id) {
        log.debug("REST request to get PoPayment : {}", id);
        PoPayment poPayment = poPaymentRepository.findOne(id);
        PoPaymentDTO poPaymentDTO = poPaymentMapper.toDto(poPayment);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(poPaymentDTO));
    }

    /**
     * DELETE  /po-payments/:id : delete the "id" poPayment.
     *
     * @param id the id of the poPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-payments/{id}")
    @Timed
    public ResponseEntity<Void> deletePoPayment(@PathVariable String id) {
        log.debug("REST request to delete PoPayment : {}", id);
        poPaymentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
