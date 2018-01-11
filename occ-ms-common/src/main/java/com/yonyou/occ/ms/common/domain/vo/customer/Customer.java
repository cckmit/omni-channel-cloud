package com.yonyou.occ.ms.common.domain.vo.customer;

import lombok.Value;

/**
 * The value object represents a customer.
 *
 * @author WangRui
 * @date 2018-01-10 09:49:14
 */
@Value
public class Customer {
    private final CustomerId id;

    private final String code;

    private final String name;
}
