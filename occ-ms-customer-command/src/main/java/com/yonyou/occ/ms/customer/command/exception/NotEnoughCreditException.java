package com.yonyou.occ.ms.customer.command.exception;

import org.axonframework.common.AxonException;

/**
 * The credit of a customer's account is not enough to subtract.
 *
 * @author WangRui
 * @date 2018-01-05 10:53:51
 */
public class NotEnoughCreditException extends AxonException {
    public NotEnoughCreditException(String message) {
        super(message);
    }

    public NotEnoughCreditException(String message, Throwable cause) {
        super(message, cause);
    }
}
