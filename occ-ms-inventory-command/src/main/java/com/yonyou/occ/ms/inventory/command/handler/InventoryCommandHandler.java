package com.yonyou.occ.ms.inventory.command.handler;

import com.yonyou.occ.ms.inventory.command.aggregate.InventoryAggregate;
import com.yonyou.occ.ms.inventory.command.inventory.ConfirmLockInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.CreateInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.IncreaseInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.LockInventoryCommand;
import com.yonyou.occ.ms.inventory.command.inventory.RevertLockInventoryCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.stereotype.Component;

/**
 * Inventory command handler, all types of Inventory commands are handled here.
 *
 * @author WangRui
 * @date 2018-01-05 11:03:56
 */
@Component
public class InventoryCommandHandler {
    private final Repository<InventoryAggregate> repository;

    public InventoryCommandHandler(Repository<InventoryAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreateInventoryCommand command) throws Exception {
        repository.newInstance(
            () -> new InventoryAggregate(command.getId(), command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(IncreaseInventoryCommand command) {
        Aggregate<InventoryAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.increase(command.getId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(LockInventoryCommand command) {
        Aggregate<InventoryAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.lock(command.getId(), command.getPoItemId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(RevertLockInventoryCommand command) {
        Aggregate<InventoryAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.revertLock(command.getId(), command.getPoItemId()));
    }

    @CommandHandler
    public void handle(ConfirmLockInventoryCommand command) {
        Aggregate<InventoryAggregate> aggregate = repository.load(command.getId().toString());
        aggregate.execute(a -> a.confirmLock(command.getId(), command.getPoItemId()));
    }
}
