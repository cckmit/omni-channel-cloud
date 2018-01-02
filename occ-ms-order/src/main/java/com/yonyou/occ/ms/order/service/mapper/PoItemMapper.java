package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.PoItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PoItem and its DTO PoItemDTO.
 */
@Mapper(componentModel = "spring", uses = {PoStateMapper.class, PurchaseOrderMapper.class})
public interface PoItemMapper extends EntityMapper<PoItemDTO, PoItem> {

    @Mapping(source = "poItemState.id", target = "poItemStateId")
    @Mapping(source = "poItemState.name", target = "poItemStateName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "purchaseOrder.code", target = "purchaseOrderCode")
    PoItemDTO toDto(PoItem poItem);

    @Mapping(source = "poItemStateId", target = "poItemState")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PoItem toEntity(PoItemDTO poItemDTO);

    default PoItem fromId(String id) {
        if (id == null) {
            return null;
        }
        PoItem poItem = new PoItem();
        poItem.setId(id);
        return poItem;
    }
}
