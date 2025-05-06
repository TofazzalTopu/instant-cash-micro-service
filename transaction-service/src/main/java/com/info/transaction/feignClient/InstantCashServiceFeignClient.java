package com.info.transaction.feignClient;

import com.info.dto.remittance.RemittanceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INSTANT-CASH-API-SERVICE")
public interface InstantCashServiceFeignClient {

   @GetMapping("/instant-cash/remittance/{reference}")
   RemittanceDataDTO findByReference(@PathVariable String reference);

}
