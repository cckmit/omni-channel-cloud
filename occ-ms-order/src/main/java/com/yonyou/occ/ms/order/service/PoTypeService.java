package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.PoType;
import com.yonyou.occ.ms.order.repository.PoTypeRepository;
import com.yonyou.occ.ms.order.service.dto.PoTypeDTO;
import com.yonyou.occ.ms.order.service.mapper.PoTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PoType.
 */
@Service
@Transactional
public class PoTypeService {

    private final Logger log = LoggerFactory.getLogger(PoTypeService.class);

    private final PoTypeRepository poTypeRepository;

    private final PoTypeMapper poTypeMapper;

    public PoTypeService(PoTypeRepository poTypeRepository, PoTypeMapper poTypeMapper) {
        this.poTypeRepository = poTypeRepository;
        this.poTypeMapper = poTypeMapper;
    }

    /**
     * Save a poType.
     *
     * @param poTypeDTO the entity to save
     * @return the persisted entity
     */
    public PoTypeDTO save(PoTypeDTO poTypeDTO) {
        log.debug("Request to save PoType : {}", poTypeDTO);
        PoType poType = poTypeMapper.toEntity(poTypeDTO);
        poType = poTypeRepository.save(poType);
        return poTypeMapper.toDto(poType);
    }

    /**
     * Get all the poTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PoTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PoTypes");
        return poTypeRepository.findAll(pageable)
            .map(poTypeMapper::toDto);
    }

    /**
     * Get one poType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PoTypeDTO findOne(Long id) {
        log.debug("Request to get PoType : {}", id);
        PoType poType = poTypeRepository.findOne(id);
        return poTypeMapper.toDto(poType);
    }

    /**
     * Delete the poType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PoType : {}", id);
        poTypeRepository.delete(id);
    }
}
