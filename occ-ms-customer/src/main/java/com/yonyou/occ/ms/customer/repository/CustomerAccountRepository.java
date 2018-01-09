package com.yonyou.occ.ms.customer.repository;

import com.yonyou.occ.ms.customer.domain.CustomerAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, String> {
    Page<CustomerAccount> findByCustomerId(Pageable pageable, String customerId);
}
