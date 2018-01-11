package com.yonyou.occ.ms.common.domain.vo.product;

import lombok.Value;

/**
 * The value object represents a product.
 *
 * @author WangRui
 * @date 2018-01-10 09:20:58
 */
@Value
public class Product {
    private final ProductId id;

    private final String code;

    private final String name;
}
