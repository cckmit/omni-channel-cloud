package com.yonyou.occ.ms.common.domain.vo.order;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.vo.product.Product;
import com.yonyou.occ.ms.common.domain.vo.product.ProductCategory;
import lombok.Data;

/**
 * The value object represents a purchase order item.
 *
 * @author WangRui
 * @date 2018-01-10 09:20:58
 */
@Data
public class PoItem {
    private final PoItemId id;

    private final ProductCategory productCategory;

    private final Product product;

    private final BigDecimal price;

    private final BigDecimal quantity;

    private boolean inventoryLocked;
}
