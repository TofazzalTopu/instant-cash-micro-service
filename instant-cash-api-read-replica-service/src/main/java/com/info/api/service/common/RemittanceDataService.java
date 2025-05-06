package com.info.api.service.common;


import com.info.api.entity.RemittanceData;
import com.info.dto.remittance.RemittanceDataDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RemittanceDataService {


    List<String> findAllReferenceByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers);

    List<String> findAllByExchangeCodeAndReferenceNumbers(String exchangeCode, List<String> referenceNumbers);

    Optional<RemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);
    Optional<RemittanceDataDTO> findBydReferenceNo(String referenceNo);

    Optional<RemittanceDataDTO> findAndSaveRemittanceData(String referenceNo);

    Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(String exchangeCode, String referenceNo, List<String> processStatuses);

    List<RemittanceDataDTO> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo);

    List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses);

    Optional<RemittanceDataDTO> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus);

    List<RemittanceDataDTO> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status);


}
