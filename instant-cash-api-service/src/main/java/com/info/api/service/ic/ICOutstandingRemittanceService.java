package com.info.api.service.ic;


import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.entity.RemittanceData;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface ICOutstandingRemittanceService {

    List<RemittanceData> fetchICOutstandingRemittance(@NotBlank ICExchangePropertyDTO icExchangePropertyDTO);


}
