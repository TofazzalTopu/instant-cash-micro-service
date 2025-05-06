package com.info.accounts.repository;

import com.info.accounts.entity.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
    Optional<AccountBalance> findById(Long id);
    Optional<AccountBalance> findByAccountNumber(String accountId);
    Optional<AccountBalance> findByInternalAccountNumber(Long internalAccountNumber);
}
