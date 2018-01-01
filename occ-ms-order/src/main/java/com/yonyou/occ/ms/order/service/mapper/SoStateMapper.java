package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.SoStateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SoState and its DTO SoStateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SoStateMapper extends EntityMapper<SoStateDTO, SoState> {

    

    

    default SoState fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoState soState = new SoState();
        soState.setId(id);
        return soState;
    }
}
