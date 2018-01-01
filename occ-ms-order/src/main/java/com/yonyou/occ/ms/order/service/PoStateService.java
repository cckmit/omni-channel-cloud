package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.PoState;
import com.yonyou.occ.ms.order.repository.PoStateRepository;
import com.yonyou.occ.ms.order.service.dto.PoStateDTO;
import com.yonyou.occ.ms.order.service.mapper.PoStateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PoState.
 */
@Service
@Transactional
public class PoStateService {

    private final Logger log = LoggerFactory.getLogger(PoStateService.class);

    private final PoStateRepository poStateRepository;

    private final PoStateMapper poStateMapper;

    public PoStateService(PoStateRepository poStateRepository, PoStateMapper poStateMapper) {
        this.poStateRepository = poStateRepository;
        this.poStateMapper = poStateMapper;
    }

    /**
     * Save a poState.
     *
     * @param poStateDTO the entity to save
     * @return the persisted entity
     */
    public PoStateDTO save(PoStateDTO poStateDTO) {
        log.debug("Request to save PoState : {}", poStateDTO);
        PoState poState = poStateMapper.toEntity(poStateDTO);
        poState = poStateRepository.save(poState);
        return poStateMapper.toDto(poState);
    }

    /**
     * Get all the poStates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PoStateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PoStates");
        return poStateRepository.findAll(pageable)
            .map(poStateMapper::toDto);
    }

    /**
     * Get one poState by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PoStateDTO findOne(Long id) {
        log.debug("Request to get PoState : {}", id);
        PoState poState = poStateRepository.findOne(id);
        return poStateMapper.toDto(poState);
    }

    /**
     * Delete the poState by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PoState : {}", id);
        poStateRepository.delete(id);
    }
}
