package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.PoPayment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PoPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoPaymentRepository extends JpaRepository<PoPayment, Long> {

}
