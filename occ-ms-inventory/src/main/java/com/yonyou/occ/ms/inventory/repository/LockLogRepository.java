package com.yonyou.occ.ms.inventory.repository;

import com.yonyou.occ.ms.inventory.domain.LockLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LockLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LockLogRepository extends JpaRepository<LockLog, Long> {

}
