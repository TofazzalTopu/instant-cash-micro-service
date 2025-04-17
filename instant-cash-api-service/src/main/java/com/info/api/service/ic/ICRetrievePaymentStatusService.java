package com.info.api.service.ic;


import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;

import javax.validation.constraints.NotBlank;

public interface ICRetrievePaymentStatusService<T> {

    APIResponse<T> getPaymentStatus(@NotBlank ICExchangePropertyDTO dto, @NotBlank String referenceNo);

}
