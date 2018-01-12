package com.yonyou.occ.ms.order.enums;

/**
 * Enumeration of purchase order state.
 *
 * @author WangRui
 * @date 2018-01-10 10:57:00
 */
public enum PoStateEnum {
    CREATED("01", "已保存"),
    PAID("02", "已支付"),
    SUBMITTED("03", "已提交");

    private final String code;

    private final String description;

    PoStateEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Return the code of this operation type.
     */
    public String getCode() {
        return code;
    }

    /**
     * Return the description of this operation type.
     */
    public String getDescription() {
        return description;
    }
}
