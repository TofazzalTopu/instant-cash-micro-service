package com.info.api.service.impl.ic;

import com.info.api.entity.ICCashRemittanceData;
import com.info.api.repository.ICCashRemittanceDataRepository;
import com.info.api.service.ic.ICCashRemittanceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.info.dto.constants.Constants.CACHE_NAME_IC_CASH_REMITTANCE_DATA;

@Service
@RequiredArgsConstructor
public class ICCashRemittanceDataServiceImpl implements ICCashRemittanceDataService {

    private final ICCashRemittanceDataRepository icCashRemittanceDataRepository;

    @Override
    @CachePut(value = CACHE_NAME_IC_CASH_REMITTANCE_DATA, key = "#icCashRemittanceData.id")
    public ICCashRemittanceData save(ICCashRemittanceData icCashRemittanceData) {
        return icCashRemittanceDataRepository.save(icCashRemittanceData);
    }

    @Override
    @Cacheable(value = CACHE_NAME_IC_CASH_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo, #middlewarePush, #processStatus)")
    public Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus) {
        return icCashRemittanceDataRepository.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, referenceNo, middlewarePush, processStatus);
    }

    @Override
    @Cacheable(value = CACHE_NAME_IC_CASH_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#exchangeCode, #referenceNo)")
    public Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return icCashRemittanceDataRepository.findFirstByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    @CacheEvict(value = CACHE_NAME_IC_CASH_REMITTANCE_DATA, key = "T(java.util.Objects).hash(#icCashRemittanceData.id)")
    public void delete(ICCashRemittanceData icCashRemittanceData) {
        icCashRemittanceDataRepository.delete(icCashRemittanceData);
    }


}
