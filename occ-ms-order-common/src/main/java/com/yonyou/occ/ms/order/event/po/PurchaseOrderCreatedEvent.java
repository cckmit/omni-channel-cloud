package com.yonyou.occ.ms.order.event.po;

import java.math.BigDecimal;
import java.util.List;

import com.yonyou.occ.ms.common.domain.AbstractDomainEvent;
import com.yonyou.occ.ms.common.domain.vo.customer.Customer;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccount;
import com.yonyou.occ.ms.common.domain.vo.order.PoItem;
import com.yonyou.occ.ms.common.domain.vo.order.PoTypeId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import lombok.Value;

/**
 * The event occurs when a purchase order is created.
 *
 * @author WangRui
 * @date 2018-01-10 09:01:56
 */
@Value
public class PurchaseOrderCreatedEvent extends AbstractDomainEvent {
    private final PurchaseOrderId id;

    private final String code;

    private final BigDecimal totalAmount;

    private final PoTypeId poTypeId;

    private final Customer customer;

    private final CustomerAccount customerAccount;

    private final List<PoItem> poItems;
}
