package com.yonyou.occ.ms.customer.service.mapper;

import com.yonyou.occ.ms.customer.domain.*;
import com.yonyou.occ.ms.customer.service.dto.CustomerAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerAccount and its DTO CustomerAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustomerAccountMapper extends EntityMapper<CustomerAccountDTO, CustomerAccount> {
    @Override
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    CustomerAccountDTO toDto(CustomerAccount customerAccount);

    @Override
    @Mapping(source = "customerId", target = "customer")
    CustomerAccount toEntity(CustomerAccountDTO customerAccountDTO);

    default CustomerAccount fromId(String id) {
        if (id == null) {
            return null;
        }
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setId(id);
        return customerAccount;
    }
}
