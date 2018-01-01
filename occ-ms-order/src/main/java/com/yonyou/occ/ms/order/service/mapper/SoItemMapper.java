package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.SoItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SoItem and its DTO SoItemDTO.
 */
@Mapper(componentModel = "spring", uses = {SoStateMapper.class, SaleOrderMapper.class})
public interface SoItemMapper extends EntityMapper<SoItemDTO, SoItem> {

    @Mapping(source = "soItemState.id", target = "soItemStateId")
    @Mapping(source = "soItemState.name", target = "soItemStateName")
    @Mapping(source = "saleOrder.id", target = "saleOrderId")
    @Mapping(source = "saleOrder.code", target = "saleOrderCode")
    SoItemDTO toDto(SoItem soItem); 

    @Mapping(source = "soItemStateId", target = "soItemState")
    @Mapping(source = "saleOrderId", target = "saleOrder")
    SoItem toEntity(SoItemDTO soItemDTO);

    default SoItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoItem soItem = new SoItem();
        soItem.setId(id);
        return soItem;
    }
}
