package com.info.transaction.service;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.transaction.feignclient.AccountServiceFeignClient;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class AccountBalanceRetryingService {

    private final AccountServiceFeignClient accountServiceFeignClient;

//    @Retryable(
//            value = {FeignException.class},
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 2000)
//    )
    public AccountBalanceDTO updateAccountBalanceWithRetry(PaymentDTO dto, String userId, String password) {
        return accountServiceFeignClient.updateAccountBalance(dto);
    }
}
