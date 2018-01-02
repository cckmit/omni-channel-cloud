package com.yonyou.occ.ms.order.service;

import java.util.UUID;

import com.yonyou.occ.ms.order.domain.SoType;
import com.yonyou.occ.ms.order.repository.SoTypeRepository;
import com.yonyou.occ.ms.order.service.dto.SoTypeDTO;
import com.yonyou.occ.ms.order.service.mapper.SoTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SoType.
 */
@Service
@Transactional
public class SoTypeService {

    private final Logger log = LoggerFactory.getLogger(SoTypeService.class);

    private final SoTypeRepository soTypeRepository;

    private final SoTypeMapper soTypeMapper;

    public SoTypeService(SoTypeRepository soTypeRepository, SoTypeMapper soTypeMapper) {
        this.soTypeRepository = soTypeRepository;
        this.soTypeMapper = soTypeMapper;
    }

    /**
     * Save a soType.
     *
     * @param soTypeDTO the entity to save
     * @return the persisted entity
     */
    public SoTypeDTO save(SoTypeDTO soTypeDTO) {
        log.debug("Request to save SoType : {}", soTypeDTO);
        SoType soType = soTypeMapper.toEntity(soTypeDTO);
        soType.setId(UUID.randomUUID().toString());
        soType = soTypeRepository.save(soType);
        return soTypeMapper.toDto(soType);
    }

    /**
     * Get all the soTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SoTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoTypes");
        return soTypeRepository.findAll(pageable)
            .map(soTypeMapper::toDto);
    }

    /**
     * Get one soType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SoTypeDTO findOne(String id) {
        log.debug("Request to get SoType : {}", id);
        SoType soType = soTypeRepository.findOne(id);
        return soTypeMapper.toDto(soType);
    }

    /**
     * Delete the soType by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SoType : {}", id);
        soTypeRepository.delete(id);
    }
}
