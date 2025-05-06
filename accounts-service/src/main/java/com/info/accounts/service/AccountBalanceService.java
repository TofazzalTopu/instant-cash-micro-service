package com.info.accounts.service;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;

public interface AccountBalanceService {

    AccountBalanceDTO save(PaymentDTO paymentDTO);
    AccountBalanceDTO revertAmount(PaymentDTO paymentDTO);

    AccountBalanceDTO findById(Long id);

    AccountBalanceDTO findByAccountNumber(String accountNumber);

    AccountBalanceDTO findByInternalAccountNumber(Long internalAccountNumber);

    double getAccountBalance(Long accountId);

}
