package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.PoState;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PoState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoStateRepository extends JpaRepository<PoState, Long> {

}
