package com.info.transaction.feignclient;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountServiceFeignClient {

    @PostMapping("/accounts/balance")
    AccountBalanceDTO updateAccountBalance(@RequestBody PaymentDTO paymentDTO);

    @PutMapping("/accounts/balance")
    AccountBalanceDTO revertAmount(@RequestBody PaymentDTO paymentDTO);

}
