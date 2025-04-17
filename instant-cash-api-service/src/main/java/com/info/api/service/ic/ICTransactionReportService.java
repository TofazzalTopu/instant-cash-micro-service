package com.info.api.service.ic;

import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.TransactionReportRequestBody;

import javax.validation.constraints.NotBlank;

public interface ICTransactionReportService<T> {

    APIResponse<T> fetchICTransactionReport(@NotBlank ICExchangePropertyDTO icExchangePropertyDTO, @NotBlank TransactionReportRequestBody report);


}
