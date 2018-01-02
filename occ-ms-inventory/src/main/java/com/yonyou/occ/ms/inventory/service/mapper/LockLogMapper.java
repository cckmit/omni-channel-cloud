package com.yonyou.occ.ms.inventory.service.mapper;

import com.yonyou.occ.ms.inventory.domain.*;
import com.yonyou.occ.ms.inventory.service.dto.LockLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LockLog and its DTO LockLogDTO.
 */
@Mapper(componentModel = "spring", uses = {InventoryMapper.class})
public interface LockLogMapper extends EntityMapper<LockLogDTO, LockLog> {

    @Mapping(source = "inventory.id", target = "inventoryId")
    @Mapping(source = "inventory.productName", target = "inventoryProductName")
    LockLogDTO toDto(LockLog lockLog);

    @Mapping(source = "inventoryId", target = "inventory")
    LockLog toEntity(LockLogDTO lockLogDTO);

    default LockLog fromId(String id) {
        if (id == null) {
            return null;
        }
        LockLog lockLog = new LockLog();
        lockLog.setId(id);
        return lockLog;
    }
}
