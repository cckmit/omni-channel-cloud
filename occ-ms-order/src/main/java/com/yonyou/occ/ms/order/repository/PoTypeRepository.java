package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.PoType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PoType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoTypeRepository extends JpaRepository<PoType, String> {

}
