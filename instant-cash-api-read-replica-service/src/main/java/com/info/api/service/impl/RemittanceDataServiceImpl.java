package com.info.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.api.entity.RemittanceData;
import com.info.api.repository.RemittanceDataRepository;
import com.info.api.service.common.RemittanceDataService;
import com.info.dto.remittance.RemittanceDataDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.info.dto.constants.Constants.CACHE_NAME_REMITTANCE_DATA;

@Service
@RequiredArgsConstructor
public class RemittanceDataServiceImpl implements RemittanceDataService {

    private static final Logger logger = LoggerFactory.getLogger(RemittanceDataServiceImpl.class);

    private final ObjectMapper objectMapper;
    private final RemittanceDataRepository remittanceDataRepository;

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceDate, #referenceNumbers)")
    public List<String> findAllReferenceByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(exchangeCode, referenceDate, referenceNumbers);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNumbers)")
    public List<String> findAllByExchangeCodeAndReferenceNumbers(String exchangeCode, List<String> referenceNumbers) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceNumbers(exchangeCode, referenceNumbers);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "'optional_' + T(java.util.Objects).hash(#exchangeCode, #referenceNo)")
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "'optional_' + T(java.util.Objects).hash(#referenceNo)")
    public Optional<RemittanceDataDTO> findBydReferenceNo(String referenceNo) {
        return remittanceDataRepository.findByReferenceNo(referenceNo)
                .map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
    }

    @Override
//    @CachePut(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(##referenceNo)")
    public Optional<RemittanceDataDTO> findAndSaveRemittanceData(String referenceNo) {
        try {
            logger.info("findAndSaveRemittanceData called with referenceNo: {}", referenceNo);
            Optional<RemittanceData> remittanceDataOptional = remittanceDataRepository.findByReferenceNo(referenceNo);
            if (remittanceDataOptional.isEmpty()) return Optional.empty();
            remittanceDataOptional.get().setProcessStatus("REMITTANCE_DATA_FETCHED");
            remittanceDataRepository.save(remittanceDataOptional.get());

            return remittanceDataOptional.map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
        } catch (Exception e) {
            logger.error("Error in findAndSaveRemittanceData: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo, #processStatuses)")
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(String exchangeCode, String referenceNo, List<String> processStatuses) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNoAndProcessStatusIsNotIn(exchangeCode, referenceNo, processStatuses);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo)")
    public List<RemittanceDataDTO> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        List<RemittanceData> remittanceDataList = remittanceDataRepository.findAllByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
        return objectMapper.convertValue(remittanceDataList, objectMapper.getTypeFactory().constructCollectionType(List.class, RemittanceDataDTO.class));
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #middlewarePush, #sourceType, #processStatuses)")
    public List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses) {
        return remittanceDataRepository.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatusIn(exchangeCode, middlewarePush, sourceType, processStatuses);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo, #middlewarePush, #processStatus)")
    public Optional<RemittanceDataDTO> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus) {
        Optional<RemittanceData> optionalRemittanceData = remittanceDataRepository.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, referenceNo, middlewarePush, processStatus);
        return optionalRemittanceData.map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #sourceType, #status)")
    public List<RemittanceDataDTO> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status) {
        List<RemittanceData> remittanceDataList = remittanceDataRepository.findAllByExchangeCodeAndSourceTypeAndProcessStatus(exchangeCode, sourceType, status);
        return objectMapper.convertValue(remittanceDataList, objectMapper.getTypeFactory().constructCollectionType(List.class, RemittanceDataDTO.class));
    }


}
