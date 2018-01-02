package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.PurchaseOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrder and its DTO PurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {PoTypeMapper.class, PoStateMapper.class})
public interface PurchaseOrderMapper extends EntityMapper<PurchaseOrderDTO, PurchaseOrder> {

    @Mapping(source = "poType.id", target = "poTypeId")
    @Mapping(source = "poType.name", target = "poTypeName")
    @Mapping(source = "poState.id", target = "poStateId")
    @Mapping(source = "poState.name", target = "poStateName")
    PurchaseOrderDTO toDto(PurchaseOrder purchaseOrder);

    @Mapping(target = "poItems", ignore = true)
    @Mapping(target = "poPayments", ignore = true)
    @Mapping(source = "poTypeId", target = "poType")
    @Mapping(source = "poStateId", target = "poState")
    PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDTO);

    default PurchaseOrder fromId(String id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }
}
