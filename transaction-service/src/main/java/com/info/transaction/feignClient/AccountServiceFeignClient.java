package com.info.transaction.feignClient;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountServiceFeignClient {

    @PostMapping("/accounts/balance")
    AccountBalanceDTO updateAccountBalance(@RequestBody PaymentDTO paymentDTO);

}
