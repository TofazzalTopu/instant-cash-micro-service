package com.info.api.service.impl.ic;

import com.info.api.entity.ICCashRemittanceData;
import com.info.api.repository.ICCashRemittanceDataRepository;
import com.info.api.service.ic.ICCashRemittanceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ICCashRemittanceDataServiceImpl implements ICCashRemittanceDataService {

    private final ICCashRemittanceDataRepository icCashRemittanceDataRepository;

    @Override
    public ICCashRemittanceData save(ICCashRemittanceData remittanceData) {
        return icCashRemittanceDataRepository.save(remittanceData);
    }

    @Override
    public Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatus(String exchangeCode, String referenceNo, Integer middlewarePush, String processStatus) {
        return icCashRemittanceDataRepository.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, referenceNo, middlewarePush, processStatus);
    }

    @Override
    public Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNo(String exchangeCode, String referenceNo) {
        return icCashRemittanceDataRepository.findFirstByExchangeCodeAndReferenceNo(exchangeCode, referenceNo);
    }

    @Override
    public void delete(ICCashRemittanceData icCashRemittanceData) {
        icCashRemittanceDataRepository.delete(icCashRemittanceData);
    }


}
