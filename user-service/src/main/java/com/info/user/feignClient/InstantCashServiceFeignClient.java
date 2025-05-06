package com.info.user.feignClient;

import com.info.dto.remittance.RemittanceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INSTANT-CASH-API-SERVICE")
public interface InstantCashServiceFeignClient {

   @GetMapping("/remittance/{reference}")
   RemittanceDataDTO findById(@PathVariable String reference);

}
