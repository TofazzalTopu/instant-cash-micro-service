package com.info.api.service.ic;

import com.info.api.dto.ic.ICExchangePropertyDTO;

public interface ICConfirmPaidStatusService {

    void notifyPaidStatus(ICExchangePropertyDTO icDTO);

}
