package com.yonyou.occ.ms.customer.service;

import com.yonyou.occ.ms.customer.domain.CustomerAccount;
import com.yonyou.occ.ms.customer.repository.CustomerAccountRepository;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;
import com.yonyou.occ.ms.customer.service.mapper.CustomerAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerAccount.
 */
@Service
@Transactional
public class CustomerAccountService {

    private final Logger log = LoggerFactory.getLogger(CustomerAccountService.class);

    private final CustomerAccountRepository customerAccountRepository;

    private final CustomerAccountMapper customerAccountMapper;

    public CustomerAccountService(CustomerAccountRepository customerAccountRepository, CustomerAccountMapper customerAccountMapper) {
        this.customerAccountRepository = customerAccountRepository;
        this.customerAccountMapper = customerAccountMapper;
    }

    /**
     * Save a customerAccount.
     *
     * @param customerAccountDTO the entity to save
     * @return the persisted entity
     */
    public CustomerAccountDTO save(CustomerAccountDTO customerAccountDTO) {
        log.debug("Request to save CustomerAccount : {}", customerAccountDTO);
        CustomerAccount customerAccount = customerAccountMapper.toEntity(customerAccountDTO);
        customerAccount = customerAccountRepository.save(customerAccount);
        return customerAccountMapper.toDto(customerAccount);
    }

    /**
     * Get all the customerAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CustomerAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerAccounts");
        return customerAccountRepository.findAll(pageable)
            .map(customerAccountMapper::toDto);
    }

    /**
     * Get one customerAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerAccountDTO findOne(Long id) {
        log.debug("Request to get CustomerAccount : {}", id);
        CustomerAccount customerAccount = customerAccountRepository.findOne(id);
        return customerAccountMapper.toDto(customerAccount);
    }

    /**
     * Delete the customerAccount by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerAccount : {}", id);
        customerAccountRepository.delete(id);
    }
}
