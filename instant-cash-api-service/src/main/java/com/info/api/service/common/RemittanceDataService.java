package com.info.api.service.common;


import com.info.api.entity.RemittanceData;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RemittanceDataService {

    RemittanceData save(RemittanceData remittanceData);

    int update(String status,  String exchangeCode, String referenceNo);

    List<RemittanceData> saveAll(List<RemittanceData> remittanceDataList);

    List<String> findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers);

    List<String> findAllByExchangeCodeAndReferenceNumbers(String exchangeCode, List<String> referenceNumbers);

    Optional<RemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);
    Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(String exchangeCode, String referenceNo, List<String> processStatuses);

    List<RemittanceData> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);

    List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses);

    List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndSourceTypeAndProcessStatuses(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses);
    Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus);

    List<RemittanceData> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status);


}
