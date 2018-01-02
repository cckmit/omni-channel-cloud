package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.PoTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PoType and its DTO PoTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PoTypeMapper extends EntityMapper<PoTypeDTO, PoType> {





    default PoType fromId(String id) {
        if (id == null) {
            return null;
        }
        PoType poType = new PoType();
        poType.setId(id);
        return poType;
    }
}
