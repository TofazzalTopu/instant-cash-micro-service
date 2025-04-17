package com.info.api.service.ic;

import com.info.api.entity.ICCashRemittanceData;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ICCashRemittanceDataService {

    ICCashRemittanceData save(@NotBlank ICCashRemittanceData remittanceData);

    Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNoAndProcessStatus(@NotBlank String exchangeCode, @NotBlank String referenceNo, @NotBlank Integer middlewarePush, @NotBlank String processStatus);

    Optional<ICCashRemittanceData> findByExchangeCodeAndReferenceNo(@NotBlank String exchangeCode, @NotBlank String referenceNo);


    void delete(ICCashRemittanceData icCashRemittanceData);
}
