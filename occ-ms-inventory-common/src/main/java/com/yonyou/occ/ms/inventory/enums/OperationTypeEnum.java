package com.yonyou.occ.ms.inventory.enums;

/**
 * Enumeration of inventory operation type.
 *
 * @author WangRui
 * @date 2018-01-09 10:21:29
 */
public enum OperationTypeEnum {
    CREATE("01", "创建库存"),
    INCREASE("02", "增加库存"),
    LOCK("03", "锁定库存"),
    REVERT_LOCK("04", "撤销库存锁定"),
    CONFIRM_LOCK("05", "确认库存锁定");

    private final String code;

    private final String description;

    OperationTypeEnum(String code, String description) {
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
