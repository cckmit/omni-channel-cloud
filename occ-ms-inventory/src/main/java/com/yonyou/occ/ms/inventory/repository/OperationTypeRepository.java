package com.yonyou.occ.ms.inventory.repository;

import com.yonyou.occ.ms.inventory.domain.OperationType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OperationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, String> {
    OperationType findByCode(String code);
}
