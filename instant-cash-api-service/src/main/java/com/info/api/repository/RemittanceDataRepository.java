package com.info.api.repository;

import com.info.api.entity.RemittanceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface RemittanceDataRepository extends JpaRepository<RemittanceData, Long> {


    //    @Lock(LockModeType.READ)
    Optional<RemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);
    Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusIsNotIn(String exchangeCode, String referenceNo, List<String> processStatuses);

    List<RemittanceData> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);

    @Query(value = "SELECT DISTINCT referenceNo FROM RemittanceData WHERE exchangeCode = ?1 and referenceDate =?2 and referenceNo IN (?3)")
    List<String> findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers);

    @Query(value = "SELECT DISTINCT referenceNo FROM RemittanceData WHERE exchangeCode = ?1 and referenceNo IN (?2)")
    List<String> findAllByExchangeCodeAndReferenceNumbers(String exchangeCode, List<String> referenceNumbers);

    @Query(value = "SELECT r FROM RemittanceData r  where r.exchangeCode=?1 and r.middlewarePush = ?2 and r.sourceType=?3 and r.finalStatus IN ('02', '03', '04', '05','07','08','14','15', '22') and r.processStatus IN (?4) and r.remittanceMessageType <> 'WEB' ")
    List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatusIn(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses);

    @Query(value = "SELECT r FROM RemittanceData r WHERE r.exchangeCode = ?1 and r.middlewarePush =?2 and r.sourceType =?3 and r.processStatus IN (?4)")
    List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndSourceTypeOrProcessStatusIn(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses);

    @Query(value = "SELECT r FROM RemittanceData r WHERE r.exchangeCode = ?1 and referenceNo = ?2 and r.middlewarePush =?3 and r.processStatus = ?4")
    Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus);

    List<RemittanceData> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status);

    @Modifying
    @Query("update RemittanceData r set r.processStatus = ?1 where  exchangeCode = ?2  and r.referenceNo = ?3")
    int updateRemittanceStatusByExchangeCodeAndReferenceNo(String status, String exchangeCode, String referenceNo);

}
