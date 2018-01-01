package com.yonyou.occ.ms.inventory.service;

import com.yonyou.occ.ms.inventory.domain.OperationType;
import com.yonyou.occ.ms.inventory.repository.OperationTypeRepository;
import com.yonyou.occ.ms.inventory.service.dto.OperationTypeDTO;
import com.yonyou.occ.ms.inventory.service.mapper.OperationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OperationType.
 */
@Service
@Transactional
public class OperationTypeService {

    private final Logger log = LoggerFactory.getLogger(OperationTypeService.class);

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeMapper operationTypeMapper;

    public OperationTypeService(OperationTypeRepository operationTypeRepository, OperationTypeMapper operationTypeMapper) {
        this.operationTypeRepository = operationTypeRepository;
        this.operationTypeMapper = operationTypeMapper;
    }

    /**
     * Save a operationType.
     *
     * @param operationTypeDTO the entity to save
     * @return the persisted entity
     */
    public OperationTypeDTO save(OperationTypeDTO operationTypeDTO) {
        log.debug("Request to save OperationType : {}", operationTypeDTO);
        OperationType operationType = operationTypeMapper.toEntity(operationTypeDTO);
        operationType = operationTypeRepository.save(operationType);
        return operationTypeMapper.toDto(operationType);
    }

    /**
     * Get all the operationTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationTypes");
        return operationTypeRepository.findAll(pageable)
            .map(operationTypeMapper::toDto);
    }

    /**
     * Get one operationType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OperationTypeDTO findOne(Long id) {
        log.debug("Request to get OperationType : {}", id);
        OperationType operationType = operationTypeRepository.findOne(id);
        return operationTypeMapper.toDto(operationType);
    }

    /**
     * Delete the operationType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OperationType : {}", id);
        operationTypeRepository.delete(id);
    }
}
