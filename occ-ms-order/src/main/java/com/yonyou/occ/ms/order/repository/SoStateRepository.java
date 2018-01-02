package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.SoState;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SoState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoStateRepository extends JpaRepository<SoState, String> {

}
