package com.yonyou.occ.ms.order.command.web.rest.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/**
 * The request DTO to create a purchase order.
 *
 * @author WangRui
 * @date 2018-01-11 09:29:26
 */
@Data
public class CreatePurchaseOrderRequestDTO {
    private String poTypeId;

    private String customerId;

    private String customerAccountId;

    private Map<String, BigDecimal> products;
}
