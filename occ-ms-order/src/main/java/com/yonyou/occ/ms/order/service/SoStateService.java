package com.yonyou.occ.ms.order.service;

import java.util.UUID;

import com.yonyou.occ.ms.order.domain.SoState;
import com.yonyou.occ.ms.order.repository.SoStateRepository;
import com.yonyou.occ.ms.order.service.dto.SoStateDTO;
import com.yonyou.occ.ms.order.service.mapper.SoStateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SoState.
 */
@Service
@Transactional
public class SoStateService {

    private final Logger log = LoggerFactory.getLogger(SoStateService.class);

    private final SoStateRepository soStateRepository;

    private final SoStateMapper soStateMapper;

    public SoStateService(SoStateRepository soStateRepository, SoStateMapper soStateMapper) {
        this.soStateRepository = soStateRepository;
        this.soStateMapper = soStateMapper;
    }

    /**
     * Save a soState.
     *
     * @param soStateDTO the entity to save
     * @return the persisted entity
     */
    public SoStateDTO save(SoStateDTO soStateDTO) {
        log.debug("Request to save SoState : {}", soStateDTO);
        SoState soState = soStateMapper.toEntity(soStateDTO);
        soState.setId(UUID.randomUUID().toString());
        soState = soStateRepository.save(soState);
        return soStateMapper.toDto(soState);
    }

    /**
     * Get all the soStates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SoStateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoStates");
        return soStateRepository.findAll(pageable)
            .map(soStateMapper::toDto);
    }

    /**
     * Get one soState by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SoStateDTO findOne(String id) {
        log.debug("Request to get SoState : {}", id);
        SoState soState = soStateRepository.findOne(id);
        return soStateMapper.toDto(soState);
    }

    /**
     * Delete the soState by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SoState : {}", id);
        soStateRepository.delete(id);
    }
}
