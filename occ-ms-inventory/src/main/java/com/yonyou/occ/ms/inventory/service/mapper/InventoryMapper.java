package com.yonyou.occ.ms.inventory.service.mapper;

import com.yonyou.occ.ms.inventory.domain.*;
import com.yonyou.occ.ms.inventory.service.dto.InventoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Inventory and its DTO InventoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {





    default Inventory fromId(String id) {
        if (id == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setId(id);
        return inventory;
    }
}
