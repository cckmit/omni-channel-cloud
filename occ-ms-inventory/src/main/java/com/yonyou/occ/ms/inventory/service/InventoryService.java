package com.yonyou.occ.ms.inventory.service;

import com.yonyou.occ.ms.inventory.domain.Inventory;
import com.yonyou.occ.ms.inventory.repository.InventoryRepository;
import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;
import com.yonyou.occ.ms.inventory.service.mapper.InventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Inventory.
 */
@Service
@Transactional
public class InventoryService {

    private final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Save a inventory.
     *
     * @param inventoryDTO the entity to save
     * @return the persisted entity
     */
    public InventoryDTO save(InventoryDTO inventoryDTO) {
        log.debug("Request to save Inventory : {}", inventoryDTO);
        Inventory inventory = inventoryMapper.toEntity(inventoryDTO);
        inventory = inventoryRepository.save(inventory);
        return inventoryMapper.toDto(inventory);
    }

    /**
     * Get all the inventories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InventoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inventories");
        return inventoryRepository.findAll(pageable)
            .map(inventoryMapper::toDto);
    }

    /**
     * Get one inventory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InventoryDTO findOne(Long id) {
        log.debug("Request to get Inventory : {}", id);
        Inventory inventory = inventoryRepository.findOne(id);
        return inventoryMapper.toDto(inventory);
    }

    /**
     * Delete the inventory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventory : {}", id);
        inventoryRepository.delete(id);
    }
}
