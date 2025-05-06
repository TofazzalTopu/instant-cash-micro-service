package com.info.api.service.impl.common;

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
    @CachePut(value = CACHE_NAME_REMITTANCE_DATA, key = "#remittanceData.id")
    public RemittanceData save(RemittanceData remittanceData) {
        return remittanceDataRepository.save(remittanceData);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#status, #exchangeCode, #referenceNo)")
    public int update(String status, String exchangeCode, String referenceNo) {
        return remittanceDataRepository.updateRemittanceStatusByExchangeCodeAndReferenceNo(status, exchangeCode, referenceNo);
    }

    @Override
    public List<RemittanceData> saveAll(List<RemittanceData> remittanceDataList) {
        return remittanceDataRepository.saveAll(remittanceDataList);
    }


    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceDate, #referenceNumbers)")
    public List<String> findAllByExchangeCodeAndReferenceDateAndReferenceNumbers(String exchangeCode, Date referenceDate, List<String> referenceNumbers) {
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
    public Optional<RemittanceDataDTO> findBydReferenceNo(String referenceNo) {
        return remittanceDataRepository.findByReferenceNo(referenceNo)
                .map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
    }
    @Override
    public Optional<RemittanceDataDTO> saveAndRemittanceData(String referenceNo) {
        Optional<RemittanceData> remittanceDataOptional = remittanceDataRepository.findByReferenceNo(referenceNo);
        if(remittanceDataOptional.isPresent()) {
            remittanceDataOptional.get().setProcessStatus("REMITTANCE_DATA_SAVED");
            remittanceDataRepository.save(remittanceDataOptional.get());
        }
        return remittanceDataOptional.map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
    }

    @Override
    public Optional<RemittanceDataDTO> updateRemittanceData(String referenceNo, String status) {
        Optional<RemittanceData> remittanceDataOptional = remittanceDataRepository.findByReferenceNo(referenceNo);
        if(remittanceDataOptional.isPresent()) {
            remittanceDataOptional.get().setProcessStatus(status);
            remittanceDataRepository.save(remittanceDataOptional.get());
        }
        return remittanceDataOptional.map(remittanceData -> objectMapper.convertValue(remittanceData, RemittanceDataDTO.class));
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo, #processStatuses)")
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatusesIsNot(String exchangeCode, String referenceNo, List<String> processStatuses) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNoAndProcessStatusIsNotIn(exchangeCode, referenceNo, processStatuses);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo)")
    public List<RemittanceData> findAllByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return remittanceDataRepository.findAllByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #middlewarePush, #sourceType, #processStatuses)")
    public List<RemittanceData> findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(String exchangeCode, Integer middlewarePush, String sourceType, List<String> processStatuses) {
        return remittanceDataRepository.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatusIn(exchangeCode, middlewarePush, sourceType, processStatuses);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo, #middlewarePush, #processStatus)")
    public Optional<RemittanceData> findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus) {
        return remittanceDataRepository.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, referenceNo, middlewarePush, processStatus);
    }

    @Override
    @Cacheable(value = CACHE_NAME_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #sourceType, #status)")
    public List<RemittanceData> findAllByExchangeCodeAndSourceTypeAndProcessStatus(String exchangeCode, String sourceType, String status) {
        return remittanceDataRepository.findAllByExchangeCodeAndSourceTypeAndProcessStatus(exchangeCode, sourceType, status);
    }


}
