package com.yonyou.occ.ms.order.service;

import com.yonyou.occ.ms.order.domain.OrderCtrlRule;
import com.yonyou.occ.ms.order.repository.OrderCtrlRuleRepository;
import com.yonyou.occ.ms.order.service.dto.OrderCtrlRuleDTO;
import com.yonyou.occ.ms.order.service.mapper.OrderCtrlRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OrderCtrlRule.
 */
@Service
@Transactional
public class OrderCtrlRuleService {

    private final Logger log = LoggerFactory.getLogger(OrderCtrlRuleService.class);

    private final OrderCtrlRuleRepository orderCtrlRuleRepository;

    private final OrderCtrlRuleMapper orderCtrlRuleMapper;

    public OrderCtrlRuleService(OrderCtrlRuleRepository orderCtrlRuleRepository, OrderCtrlRuleMapper orderCtrlRuleMapper) {
        this.orderCtrlRuleRepository = orderCtrlRuleRepository;
        this.orderCtrlRuleMapper = orderCtrlRuleMapper;
    }

    /**
     * Save a orderCtrlRule.
     *
     * @param orderCtrlRuleDTO the entity to save
     * @return the persisted entity
     */
    public OrderCtrlRuleDTO save(OrderCtrlRuleDTO orderCtrlRuleDTO) {
        log.debug("Request to save OrderCtrlRule : {}", orderCtrlRuleDTO);
        OrderCtrlRule orderCtrlRule = orderCtrlRuleMapper.toEntity(orderCtrlRuleDTO);
        orderCtrlRule = orderCtrlRuleRepository.save(orderCtrlRule);
        return orderCtrlRuleMapper.toDto(orderCtrlRule);
    }

    /**
     * Get all the orderCtrlRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderCtrlRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderCtrlRules");
        return orderCtrlRuleRepository.findAll(pageable)
            .map(orderCtrlRuleMapper::toDto);
    }

    /**
     * Get one orderCtrlRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrderCtrlRuleDTO findOne(Long id) {
        log.debug("Request to get OrderCtrlRule : {}", id);
        OrderCtrlRule orderCtrlRule = orderCtrlRuleRepository.findOne(id);
        return orderCtrlRuleMapper.toDto(orderCtrlRule);
    }

    /**
     * Delete the orderCtrlRule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderCtrlRule : {}", id);
        orderCtrlRuleRepository.delete(id);
    }
}
