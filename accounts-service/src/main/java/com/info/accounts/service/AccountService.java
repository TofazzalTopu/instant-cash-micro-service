package com.info.accounts.service;

import com.info.dto.account.AccountDTO;

public interface AccountService {

    AccountDTO save(AccountDTO account);
    AccountDTO findById(Long id);
}
