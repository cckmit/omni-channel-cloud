package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.SaleOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SaleOrder and its DTO SaleOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {SoTypeMapper.class, SoStateMapper.class})
public interface SaleOrderMapper extends EntityMapper<SaleOrderDTO, SaleOrder> {

    @Mapping(source = "soType.id", target = "soTypeId")
    @Mapping(source = "soType.name", target = "soTypeName")
    @Mapping(source = "soState.id", target = "soStateId")
    @Mapping(source = "soState.name", target = "soStateName")
    SaleOrderDTO toDto(SaleOrder saleOrder);

    @Mapping(target = "soItems", ignore = true)
    @Mapping(source = "soTypeId", target = "soType")
    @Mapping(source = "soStateId", target = "soState")
    SaleOrder toEntity(SaleOrderDTO saleOrderDTO);

    default SaleOrder fromId(String id) {
        if (id == null) {
            return null;
        }
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setId(id);
        return saleOrder;
    }
}
