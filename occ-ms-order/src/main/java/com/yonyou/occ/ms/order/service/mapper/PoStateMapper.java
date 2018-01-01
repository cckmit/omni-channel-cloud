package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.PoStateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PoState and its DTO PoStateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PoStateMapper extends EntityMapper<PoStateDTO, PoState> {

    

    

    default PoState fromId(Long id) {
        if (id == null) {
            return null;
        }
        PoState poState = new PoState();
        poState.setId(id);
        return poState;
    }
}
