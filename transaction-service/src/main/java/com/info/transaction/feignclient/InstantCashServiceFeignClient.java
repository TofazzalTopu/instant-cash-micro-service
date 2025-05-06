package com.info.transaction.feignclient;

import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INSTANT-CASH-API-SERVICE")
public interface InstantCashServiceFeignClient {

    @PostMapping(Constants.INSTANT_CASH_WRITE + "/update")
    RemittanceDataDTO updateRemittanceData(@RequestParam String reference, @RequestParam String status);

}
