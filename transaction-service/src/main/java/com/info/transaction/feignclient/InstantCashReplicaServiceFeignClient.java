package com.info.transaction.feignclient;

import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INSTANT-CASH-API-READ-REPLICA-SERVICE")
public interface InstantCashReplicaServiceFeignClient {

   @GetMapping(Constants.INSTANT_CASH_READ + "/remittance/{reference}")
   RemittanceDataDTO findByReference(@PathVariable String reference);


}
