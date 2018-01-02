package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.OrderCtrlRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderCtrlRule and its DTO OrderCtrlRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {PoTypeMapper.class, SoTypeMapper.class})
public interface OrderCtrlRuleMapper extends EntityMapper<OrderCtrlRuleDTO, OrderCtrlRule> {

    @Mapping(source = "poType.id", target = "poTypeId")
    @Mapping(source = "poType.name", target = "poTypeName")
    @Mapping(source = "soType.id", target = "soTypeId")
    @Mapping(source = "soType.name", target = "soTypeName")
    OrderCtrlRuleDTO toDto(OrderCtrlRule orderCtrlRule);

    @Mapping(source = "poTypeId", target = "poType")
    @Mapping(source = "soTypeId", target = "soType")
    OrderCtrlRule toEntity(OrderCtrlRuleDTO orderCtrlRuleDTO);

    default OrderCtrlRule fromId(String id) {
        if (id == null) {
            return null;
        }
        OrderCtrlRule orderCtrlRule = new OrderCtrlRule();
        orderCtrlRule.setId(id);
        return orderCtrlRule;
    }
}
