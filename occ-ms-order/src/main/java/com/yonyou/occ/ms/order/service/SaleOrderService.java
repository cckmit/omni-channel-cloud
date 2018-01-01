package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.SaleOrder;
import com.yonyou.occ.ms.order.repository.SaleOrderRepository;
import com.yonyou.occ.ms.order.service.dto.SaleOrderDTO;
import com.yonyou.occ.ms.order.service.mapper.SaleOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SaleOrder.
 */
@Service
@Transactional
public class SaleOrderService {

    private final Logger log = LoggerFactory.getLogger(SaleOrderService.class);

    private final SaleOrderRepository saleOrderRepository;

    private final SaleOrderMapper saleOrderMapper;

    public SaleOrderService(SaleOrderRepository saleOrderRepository, SaleOrderMapper saleOrderMapper) {
        this.saleOrderRepository = saleOrderRepository;
        this.saleOrderMapper = saleOrderMapper;
    }

    /**
     * Save a saleOrder.
     *
     * @param saleOrderDTO the entity to save
     * @return the persisted entity
     */
    public SaleOrderDTO save(SaleOrderDTO saleOrderDTO) {
        log.debug("Request to save SaleOrder : {}", saleOrderDTO);
        SaleOrder saleOrder = saleOrderMapper.toEntity(saleOrderDTO);
        saleOrder = saleOrderRepository.save(saleOrder);
        return saleOrderMapper.toDto(saleOrder);
    }

    /**
     * Get all the saleOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SaleOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SaleOrders");
        return saleOrderRepository.findAll(pageable)
            .map(saleOrderMapper::toDto);
    }

    /**
     * Get one saleOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SaleOrderDTO findOne(Long id) {
        log.debug("Request to get SaleOrder : {}", id);
        SaleOrder saleOrder = saleOrderRepository.findOne(id);
        return saleOrderMapper.toDto(saleOrder);
    }

    /**
     * Delete the saleOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SaleOrder : {}", id);
        saleOrderRepository.delete(id);
    }
}
