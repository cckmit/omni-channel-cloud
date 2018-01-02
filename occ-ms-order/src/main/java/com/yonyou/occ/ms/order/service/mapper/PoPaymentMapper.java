package com.yonyou.occ.ms.order.service.mapper;

import com.yonyou.occ.ms.order.domain.*;
import com.yonyou.occ.ms.order.service.dto.PoPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PoPayment and its DTO PoPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {PurchaseOrderMapper.class})
public interface PoPaymentMapper extends EntityMapper<PoPaymentDTO, PoPayment> {

    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "purchaseOrder.code", target = "purchaseOrderCode")
    PoPaymentDTO toDto(PoPayment poPayment);

    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PoPayment toEntity(PoPaymentDTO poPaymentDTO);

    default PoPayment fromId(String id) {
        if (id == null) {
            return null;
        }
        PoPayment poPayment = new PoPayment();
        poPayment.setId(id);
        return poPayment;
    }
}
