package com.yonyou.occ.ms.inventory.command.aggregate;

import java.math.BigDecimal;

import com.yonyou.occ.ms.inventory.enums.OperationTypeEnum;
import lombok.Value;

@Value
public class InventoryOperationLog {
    private final OperationTypeEnum operationType;

    private final BigDecimal operationQuantity;
}
