package com.info.api.service.ic;


import com.info.api.dto.SearchApiRequest;
import com.info.api.dto.SearchApiResponse;
import com.info.api.dto.ic.ICExchangePropertyDTO;

import javax.validation.constraints.NotBlank;

public interface ICPaymentReceiveService {

    SearchApiResponse paymentReceive(@NotBlank ICExchangePropertyDTO dto, @NotBlank SearchApiRequest apiRequest);

}
