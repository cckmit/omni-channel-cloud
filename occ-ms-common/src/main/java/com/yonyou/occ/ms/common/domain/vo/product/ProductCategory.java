package com.yonyou.occ.ms.common.domain.vo.product;

import lombok.Value;

/**
 * The value object represents a product category.
 *
 * @author WangRui
 * @date 2018-01-10 09:20:58
 */
@Value
public class ProductCategory {
    private final ProductCategoryId id;

    private final String code;

    private final String name;
}
