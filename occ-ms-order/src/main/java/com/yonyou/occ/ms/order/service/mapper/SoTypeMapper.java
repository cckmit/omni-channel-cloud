package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.SoTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SoType and its DTO SoTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SoTypeMapper extends EntityMapper<SoTypeDTO, SoType> {

    

    

    default SoType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoType soType = new SoType();
        soType.setId(id);
        return soType;
    }
}
