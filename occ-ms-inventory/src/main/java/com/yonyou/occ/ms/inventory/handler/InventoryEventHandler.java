package com.yonyou.occ.ms.inventory.handler;

import java.math.BigDecimal;
import java.util.UUID;

import com.yonyou.occ.ms.inventory.domain.Inventory;
import com.yonyou.occ.ms.inventory.domain.LockLog;
import com.yonyou.occ.ms.inventory.domain.OperationLog;
import com.yonyou.occ.ms.inventory.domain.OperationType;
import com.yonyou.occ.ms.inventory.enums.OperationTypeEnum;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryCreatedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryIncreasedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockConfirmedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockRevertedEvent;
import com.yonyou.occ.ms.inventory.event.inventory.InventoryLockedEvent;
import com.yonyou.occ.ms.inventory.repository.InventoryRepository;
import com.yonyou.occ.ms.inventory.repository.LockLogRepository;
import com.yonyou.occ.ms.inventory.repository.OperationLogRepository;
import com.yonyou.occ.ms.inventory.repository.OperationTypeRepository;
import com.yonyou.occ.ms.inventory.web.rest.ProductService;
import com.yonyou.occ.ms.product.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * CustomerAccount event handler class. CustomerAccount events are handled here.
 * For instance, using JPA Repository to save entity.
 *
 * @author WangRui
 * @date 2018-01-05 11:10:31
 */
@Slf4j
@Component
public class InventoryEventHandler {
    private final InventoryRepository inventoryRepository;

    private final OperationTypeRepository operationTypeRepository;

    private final OperationLogRepository operationLogRepository;

    private final LockLogRepository lockLogRepository;

    private final ProductService productService;

    public InventoryEventHandler(InventoryRepository inventoryRepository,
        OperationTypeRepository operationTypeRepository, OperationLogRepository operationLogRepository,
        LockLogRepository lockLogRepository, ProductService productService) {
        this.inventoryRepository = inventoryRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.operationLogRepository = operationLogRepository;
        this.lockLogRepository = lockLogRepository;
        this.productService = productService;
    }

    @EventHandler
    public void handle(InventoryCreatedEvent event) {
        // Find product by id
        String productId = event.getProductId().getId();
        ProductDTO productDTO = productService.getProduct(productId).getBody();

        // create inventory
        Inventory inventory = new Inventory();
        inventory.setId(event.getId().getId());
        inventory.setProductId(productId);
        inventory.setProductCode(productDTO.getCode());
        inventory.setProductName(productDTO.getName());
        inventory.setToSellQuantity(event.getQuantity());
        inventory.setLockedQuantity(BigDecimal.ZERO);
        inventory.setSaledQuantity(BigDecimal.ZERO);
        inventory.setIsEnabled(true);
        inventory = inventoryRepository.save(inventory);

        // create operation log
        OperationLog operationLog = new OperationLog();
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setInventory(inventory);
        OperationType operationType = operationTypeRepository.findByCode(OperationTypeEnum.CREATE.getCode());
        operationLog.setOperationType(operationType);
        operationLog.setOperationQuantity(event.getQuantity());
        operationLogRepository.save(operationLog);

        log.debug("Inventory {} is created.", inventory);
    }

    @EventHandler
    public void handle(InventoryIncreasedEvent event) {
        // update quantity in inventory
        Inventory inventory = inventoryRepository.findOne(event.getId().getId());
        inventory.setToSellQuantity(inventory.getToSellQuantity().add(event.getQuantity()));
        inventory = inventoryRepository.save(inventory);

        // create operation log
        OperationLog operationLog = new OperationLog();
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setInventory(inventory);
        OperationType operationType = operationTypeRepository.findByCode(OperationTypeEnum.INCREASE.getCode());
        operationLog.setOperationType(operationType);
        operationLog.setOperationQuantity(event.getQuantity());
        operationLogRepository.save(operationLog);

        log.debug("Inventory {} is increased.", inventory);
    }

    @EventHandler
    public void handle(InventoryLockedEvent event) {
        // update quantity in inventory
        Inventory inventory = inventoryRepository.findOne(event.getId().getId());
        inventory.setToSellQuantity(inventory.getToSellQuantity().subtract(event.getQuantity()));
        inventory.setLockedQuantity(inventory.getLockedQuantity().add(event.getQuantity()));
        inventory = inventoryRepository.save(inventory);

        // create operation log
        OperationLog operationLog = new OperationLog();
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setInventory(inventory);
        OperationType operationType = operationTypeRepository.findByCode(OperationTypeEnum.LOCK.getCode());
        operationLog.setOperationType(operationType);
        operationLog.setOperationQuantity(event.getQuantity());
        operationLogRepository.save(operationLog);

        // create lock log
        LockLog lockLog = new LockLog();
        lockLog.setId(UUID.randomUUID().toString());
        lockLog.setInventory(inventory);
        lockLog.setLockedQuantity(event.getQuantity());
        lockLogRepository.save(lockLog);

        log.debug("Inventory {} is locked.", inventory);
    }

    @EventHandler
    public void handle(InventoryLockRevertedEvent event) {
        // delete lock log
        LockLog lockLog = lockLogRepository.findByInventoryIdAndPoItemId(event.getId().getId(),
            event.getPoItemId().getId());
        lockLog.setDr(1);
        lockLogRepository.save(lockLog);

        // update quantity in inventory
        Inventory inventory = inventoryRepository.findOne(event.getId().getId());
        BigDecimal lockedQuantity = lockLog.getLockedQuantity();
        inventory.setToSellQuantity(inventory.getToSellQuantity().add(lockedQuantity));
        inventory.setLockedQuantity(inventory.getLockedQuantity().subtract(lockedQuantity));
        inventory = inventoryRepository.save(inventory);

        // create operation log
        OperationLog operationLog = new OperationLog();
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setInventory(inventory);
        OperationType operationType = operationTypeRepository.findByCode(OperationTypeEnum.REVERT_LOCK.getCode());
        operationLog.setOperationType(operationType);
        operationLog.setOperationQuantity(lockedQuantity);
        operationLogRepository.save(operationLog);

        log.debug("The lock of inventory {} is reverted.", inventory);
    }

    @EventHandler
    public void handle(InventoryLockConfirmedEvent event) {
        // delete lock log
        LockLog lockLog = lockLogRepository.findByInventoryIdAndPoItemId(event.getId().getId(),
            event.getPoItemId().getId());
        lockLog.setDr(1);
        lockLogRepository.save(lockLog);

        // update quantity in inventory
        Inventory inventory = inventoryRepository.findOne(event.getId().getId());
        BigDecimal lockedQuantity = lockLog.getLockedQuantity();
        inventory.setLockedQuantity(inventory.getLockedQuantity().subtract(lockedQuantity));
        inventory.setSaledQuantity(inventory.getSaledQuantity().add(lockedQuantity));
        inventory = inventoryRepository.save(inventory);

        // create operation log
        OperationLog operationLog = new OperationLog();
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setInventory(inventory);
        OperationType operationType = operationTypeRepository.findByCode(OperationTypeEnum.CONFIRM_LOCK.getCode());
        operationLog.setOperationType(operationType);
        operationLog.setOperationQuantity(lockedQuantity);
        operationLogRepository.save(operationLog);

        log.debug("The lock of inventory {} is confirmed.", inventory);
    }
}
