package com.info.api.service.ic;


import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICUnlockTransactionResponseDTO;

import javax.validation.constraints.NotBlank;

public interface ICUnlockRemittanceService {

    APIResponse<String> unlockICOutstandingRemittance(@NotBlank String referenceNo, @NotBlank ICExchangePropertyDTO icExchangePropertyDTO);

}
