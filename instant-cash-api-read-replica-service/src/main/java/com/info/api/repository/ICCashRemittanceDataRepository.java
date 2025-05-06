package com.info.api.repository;

import com.info.api.entity.ICCashRemittanceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICCashRemittanceDataRepository extends JpaRepository<ICCashRemittanceData, Long> {
    Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus);

    Optional<ICCashRemittanceData> findFirstByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);
}
