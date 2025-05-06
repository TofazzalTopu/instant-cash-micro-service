package com.info.accounts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.accounts.entity.Account;
import com.info.accounts.exception.InvalidRequestException;
import com.info.accounts.repository.AccountRepository;
import com.info.accounts.service.AccountService;
import com.info.dto.account.AccountDTO;
import com.info.dto.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    @Override
    @CachePut(value = Constants.CACHE_NAME_ACCOUNT_BALANCE, key = "#account.accountNumber")
    public AccountDTO save(AccountDTO accountDTO) {
        if (accountDTO == null) {
            throw new InvalidRequestException("AccountDTO must not be null");
        }

        Account accountEntity = objectMapper.convertValue(accountDTO, Account.class);
        Account savedAccount = accountRepository.save(accountEntity);
        return objectMapper.convertValue(savedAccount, AccountDTO.class);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_ACCOUNT_BALANCE, key = "#id")
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return objectMapper.convertValue(account, AccountDTO.class);
    }
}
