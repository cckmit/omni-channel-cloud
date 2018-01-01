package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.SoItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoItemRepository extends JpaRepository<SoItem, Long> {

}
