package com.yonyou.occ.ms.order.repository;

import com.yonyou.occ.ms.order.domain.OrderCtrlRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderCtrlRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderCtrlRuleRepository extends JpaRepository<OrderCtrlRule, String> {

}
