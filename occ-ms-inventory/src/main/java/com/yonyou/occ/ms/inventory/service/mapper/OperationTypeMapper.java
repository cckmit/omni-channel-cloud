package com.yonyou.occ.ms.inventory.service.mapper;

import com.yonyou.occ.ms.inventory.domain.*;
import com.yonyou.occ.ms.inventory.service.dto.OperationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OperationType and its DTO OperationTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OperationTypeMapper extends EntityMapper<OperationTypeDTO, OperationType> {





    default OperationType fromId(String id) {
        if (id == null) {
            return null;
        }
        OperationType operationType = new OperationType();
        operationType.setId(id);
        return operationType;
    }
}
