package com.yonyou.occ.ms.inventory.command.exception;

import org.axonframework.common.AxonException;

/**
 * The product's inventory is not enough.
 *
 * @author WangRui
 * @date 2018-01-08 16:41:02
 */
public class InventoryNotEnoughException extends AxonException {
    public InventoryNotEnoughException(String message) {
        super(message);
    }

    public InventoryNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }
}
