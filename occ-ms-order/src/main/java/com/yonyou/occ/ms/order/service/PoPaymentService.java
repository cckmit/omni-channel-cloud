package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.PoPayment;
import com.yonyou.occ.ms.order.repository.PoPaymentRepository;
import com.yonyou.occ.ms.order.service.dto.PoPaymentDTO;
import com.yonyou.occ.ms.order.service.mapper.PoPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PoPayment.
 */
@Service
@Transactional
public class PoPaymentService {

    private final Logger log = LoggerFactory.getLogger(PoPaymentService.class);

    private final PoPaymentRepository poPaymentRepository;

    private final PoPaymentMapper poPaymentMapper;

    public PoPaymentService(PoPaymentRepository poPaymentRepository, PoPaymentMapper poPaymentMapper) {
        this.poPaymentRepository = poPaymentRepository;
        this.poPaymentMapper = poPaymentMapper;
    }

    /**
     * Save a poPayment.
     *
     * @param poPaymentDTO the entity to save
     * @return the persisted entity
     */
    public PoPaymentDTO save(PoPaymentDTO poPaymentDTO) {
        log.debug("Request to save PoPayment : {}", poPaymentDTO);
        PoPayment poPayment = poPaymentMapper.toEntity(poPaymentDTO);
        poPayment = poPaymentRepository.save(poPayment);
        return poPaymentMapper.toDto(poPayment);
    }

    /**
     * Get all the poPayments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PoPaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PoPayments");
        return poPaymentRepository.findAll(pageable)
            .map(poPaymentMapper::toDto);
    }

    /**
     * Get one poPayment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PoPaymentDTO findOne(Long id) {
        log.debug("Request to get PoPayment : {}", id);
        PoPayment poPayment = poPaymentRepository.findOne(id);
        return poPaymentMapper.toDto(poPayment);
    }

    /**
     * Delete the poPayment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PoPayment : {}", id);
        poPaymentRepository.delete(id);
    }
}
