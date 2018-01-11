package com.yonyou.occ.ms.common.domain.vo.customer;

import lombok.Value;

/**
 * The value object represents a customer account.
 *
 * @author WangRui
 * @date 2018-01-10 09:49:14
 */
@Value
public class CustomerAccount {
    private final CustomerAccountId id;

    private final String code;

    private final String name;
}
