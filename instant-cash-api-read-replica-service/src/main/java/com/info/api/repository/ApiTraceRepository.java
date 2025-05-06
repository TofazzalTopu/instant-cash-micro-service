package com.info.api.repository;

import com.info.api.entity.ApiTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface ApiTraceRepository extends JpaRepository<ApiTrace, Long> {

	@Query(value = "SELECT REM_API_TRACE_TRAN_NO_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	long getApiTranSequence();

	@Query(value = "SELECT PKG_PB_GLOBAL.FN_GET_CURR_BUS_DATE(1) FROM DUAL", nativeQuery = true)
	Timestamp getCurrentBusinessDate();

	@Query(value = "SELECT RAT.ID ID from REM_API_TRACE RAT WHERE  RAT.REQ_TYPE = 'PAYMENT'  AND RAT.STATUS = 'ERROR_TIMEOUT' AND (RAT.CANCEL_STATUS IS NULL OR RAT.CANCEL_STATUS = 'UNSETTLED') "
			+ "AND RAT.TRY_COUNT <= :tryCount "
			+ "UNION "
			+ "SELECT T.ID ID FROM REM_API_TRACE T WHERE T.TRAN_NO IN ( "
			+ "SELECT TO_NUMBER(R.REMCSP_SPOT_ID) ID FROM REMCASHPAY R WHERE R.REMCSP_ENTRY_TYPE = 'R' AND R.PAYOUT_STATUS = '1000' AND R.REMCSP_REJ_ON IS NOT NULL "
			+ ")  AND T.TRY_COUNT <= :tryCount ", nativeQuery = true)
	List<Long> getCancelIds(@Param("tryCount") int tryCount);
	
	@Query(value = "select a from ApiTrace a where a.tranno = :tranNo and a.requestType = :requestType ")
	Optional<ApiTrace> findByTranNo(@Param("tranNo") String tranNo,@Param("requestType") String requestType);
	
	@Query(value = "SELECT REMCSP_REF_DATE FROM REMCASHPAY R where R.REMCSP_ENTRY_TYPE = 'R' AND R.REMCSP_EXCHOU_CODE = :exchangeCode and R.REMCSP_PIN = :pin and R.REMCSP_TRAN_DATE = :tranDate and R.REMCSP_REJ_ON is NULL ", nativeQuery = true)
	Timestamp getRefDate(@Param("exchangeCode") String exchangeCode, @Param("pin") String pin, @Param("tranDate") Date tranDate); 
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE REMCASHPAY R SET R.PAYOUT_STATUS = :payoutStatus WHERE R.REMCSP_ENTRY_TYPE = 'R' AND R.PAYOUT_STATUS = '1000' AND R.REMCSP_REJ_ON IS NOT NULL AND R.REMCSP_SPOT_ID IN (:spotId) ", nativeQuery = true)
	void updatePayoutStatus(@Param("payoutStatus") String payoutStatus, @Param("spotId") List<String> spotIds);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE REMCASHPAY R SET R.REMCSP_IS_SYNCED = '1' where R.REMCSP_ENTRY_TYPE = 'R' AND R.REMCSP_EXCHOU_CODE = :exchangeCode and R.REMCSP_PIN = :pin and R.REMCSP_TRAN_DATE = :tranDate and R.REMCSP_REJ_ON is NULL ", nativeQuery = true)
	void updateSyncFlag(@Param("exchangeCode") String exchangeCode, @Param("pin") String pin, @Param("tranDate") Date tranDate);
	
	
}
