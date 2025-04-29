package com.info.accounts.service;

import com.info.accounts.entity.Account;

public interface AccountService {

    Account save(Account account);
    Account findById(Long id);
}
