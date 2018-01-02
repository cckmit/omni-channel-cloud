package com.yonyou.occ.ms.inventory.repository;

import com.yonyou.occ.ms.inventory.domain.OperationLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OperationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, String> {

}
