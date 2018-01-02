package com.yonyou.occ.ms.inventory.repository;

import com.yonyou.occ.ms.inventory.domain.Inventory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

}
