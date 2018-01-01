package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.PurchaseOrder;
import com.yonyou.occ.ms.order.repository.PurchaseOrderRepository;
import com.yonyou.occ.ms.order.service.dto.PurchaseOrderDTO;
import com.yonyou.occ.ms.order.service.mapper.PurchaseOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PurchaseOrder.
 */
@Service
@Transactional
public class PurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrderDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
        log.debug("Request to save PurchaseOrder : {}", purchaseOrderDTO);
        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderDTO);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrderMapper.toDto(purchaseOrder);
    }

    /**
     * Get all the purchaseOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrderRepository.findAll(pageable)
            .map(purchaseOrderMapper::toDto);
    }

    /**
     * Get one purchaseOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PurchaseOrderDTO findOne(Long id) {
        log.debug("Request to get PurchaseOrder : {}", id);
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
        return purchaseOrderMapper.toDto(purchaseOrder);
    }

    /**
     * Delete the purchaseOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.delete(id);
    }
}
