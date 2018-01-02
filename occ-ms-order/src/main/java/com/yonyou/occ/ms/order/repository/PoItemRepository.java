package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.PoItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoItemRepository extends JpaRepository<PoItem, String> {

}
