package com.info.api.service.impl.common;

import com.info.api.entity.RemittanceData;
import com.info.api.repository.RemittanceDataRepository;
import com.info.api.service.common.RemittanceDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemittanceDataServiceImpl implements RemittanceDataService {

    private static final Logger logger = LoggerFactory.getLogger(RemittanceDataServiceImpl.class);

    private final RemittanceDataRepository remittanceDataRepository;
    @Override
    public RemittanceData save(RemittanceData remittanceData) {
        return remittanceDataRepository.save(remittanceData);
    }

    @Override
    public int update(String status, String exchangeCode, String referenceNo) {
        return remittanceDataRepository.updateRemittanceStatusByExchangeCodeAndReferenceNo(status, exchangeCode, referenceNo);
    }

    @Override
    public List<RemittanceData> saveAll(List<RemittanceData> remittanceDataList) {
        return remittanceDataRepository.saveAll(remittanceDataList);
    }


    @Override
    public List<String> findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(exchangeCode, referenceDate, referenceNumbers);
    }

    @Override
    public List<String> findAllByExchangeCodeAndReferenceNumbers(String exchangeCode, List<String> referenceNumbers) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceNumbers(exchangeCode, referenceNumbers);
    }

    @Override
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(String exchangeCode, String referenceNo, List<String> processStatuses) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNoAndProcessStatusIsNotIn(exchangeCode, referenceNo, processStatuses);
    }

    @Override
    public List<RemittanceData> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    public List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses) {
        return remittanceDataRepository.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatusIn(exchangeCode, middlewarePush, sourceType, processStatuses);
    }
    public List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndSourceTypeAndProcessStatuses(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses) {
        return remittanceDataRepository.findAllByExchangeCodeAndMiddlewarePushAndSourceTypeOrProcessStatusIn(exchangeCode, middlewarePush, sourceType, processStatuses);
    }

    @Override
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, referenceNo, middlewarePush, processStatus);
    }


    @Override
    public List<RemittanceData> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status) {
        return remittanceDataRepository.findAllByExchangeCodeAndSourceTypeAndProcessStatus(exchangeCode, sourceType, status);
    }


}
