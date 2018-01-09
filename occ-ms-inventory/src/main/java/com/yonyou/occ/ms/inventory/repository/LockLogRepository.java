package com.yonyou.occ.ms.inventory.repository;

import com.yonyou.occ.ms.inventory.domain.LockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the LockLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LockLogRepository extends JpaRepository<LockLog, String> {
    /**
     * Find one LockLog by inventory id and purchase order item id.
     *
     * @param inventoryId ID of inventory.
     * @param poItemId ID of purchase order item.
     * @return The LockLog.
     */
    LockLog findByInventoryIdAndPoItemId(String inventoryId, String poItemId);
}
