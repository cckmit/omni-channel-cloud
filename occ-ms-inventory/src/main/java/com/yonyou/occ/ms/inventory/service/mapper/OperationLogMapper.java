package com.yonyou.occ.ms.inventory.service.mapper;

import com.yonyou.occ.ms.inventory.domain.*;
import com.yonyou.occ.ms.inventory.service.dto.OperationLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OperationLog and its DTO OperationLogDTO.
 */
@Mapper(componentModel = "spring", uses = {OperationTypeMapper.class, InventoryMapper.class})
public interface OperationLogMapper extends EntityMapper<OperationLogDTO, OperationLog> {

    @Mapping(source = "operationType.id", target = "operationTypeId")
    @Mapping(source = "operationType.name", target = "operationTypeName")
    @Mapping(source = "inventory.id", target = "inventoryId")
    @Mapping(source = "inventory.productName", target = "inventoryProductName")
    OperationLogDTO toDto(OperationLog operationLog); 

    @Mapping(source = "operationTypeId", target = "operationType")
    @Mapping(source = "inventoryId", target = "inventory")
    OperationLog toEntity(OperationLogDTO operationLogDTO);

    default OperationLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        OperationLog operationLog = new OperationLog();
        operationLog.setId(id);
        return operationLog;
    }
}
