package com.yonyou.occ.ms.inventory.command.aggregate;

import java.math.BigDecimal;

import com.yonyou.occ.ms.common.domain.vo.order.PoItemId;
import lombok.Value;

@Value
public class InventoryLockLog {
    private final BigDecimal lockedQuantity;

    private final PoItemId poItemId;
}