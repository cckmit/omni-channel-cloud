package com.yonyou.occ.ms.order.command.po;

import java.math.BigDecimal;
import java.util.Map;

import com.yonyou.occ.ms.common.domain.AbstractDomainCommand;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerAccountId;
import com.yonyou.occ.ms.common.domain.vo.customer.CustomerId;
import com.yonyou.occ.ms.common.domain.vo.order.PoTypeId;
import com.yonyou.occ.ms.common.domain.vo.order.PurchaseOrderId;
import com.yonyou.occ.ms.common.domain.vo.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * The command to create a purchase order.
 *
 * @author WangRui
 * @date 2018-01-09 16:21:06
 */
@Data
@AllArgsConstructor
public class CreatePurchaseOrderCommand extends AbstractDomainCommand {
    @TargetAggregateIdentifier
    private PurchaseOrderId id;

    private String code;

    private PoTypeId poTypeId;

    private CustomerId customerId;

    private CustomerAccountId customerAccountId;

    private Map<ProductId, BigDecimal> products;
}
