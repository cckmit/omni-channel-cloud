package com.yonyou.occ.ms.common.domain.vo.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Value;

/**
 * The value object represents a purchase order payment.
 *
 * @author WangRui
 * @date 2018-01-10 09:20:58
 */
@Value
public class PoPayment {
    private final PoPaymentId id;

    private final BigDecimal amount;

    private final Boolean paymentSuccessful;

    private final String failedReason;

    private final ZonedDateTime timePaid;
}
