package com.info.accounts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.accounts.entity.AccountBalance;
import com.info.accounts.exception.InvalidRequestException;
import com.info.accounts.repository.AccountBalanceRepository;
import com.info.accounts.service.AccountBalanceService;
import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.dto.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountBalanceServiceImpl implements AccountBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceServiceImpl.class.getName());

    private final ObjectMapper objectMapper;
    private final AccountBalanceRepository accountBalanceRepository;


    @Override
//    @CachePut(value = Constants.CACHE_NAME_ACCOUNT, key = "#account.accountNumber")
    public AccountBalanceDTO save(PaymentDTO paymentDTO) {
        try {
            if (paymentDTO == null || paymentDTO.getAccountNumber() == null || Objects.isNull(paymentDTO.getAmount())) {
                throw new InvalidRequestException("paymentDTO must not be null");
            }

            AccountBalance accountBalance = new AccountBalance();
            Optional<AccountBalance> optionalAccountBalance = accountBalanceRepository.findByAccountNumber(paymentDTO.getAccountNumber());
            if (optionalAccountBalance.isPresent()) {
                accountBalance = optionalAccountBalance.get();
            }
            double newBalance = Objects.nonNull(accountBalance.getAccountBalance()) ? accountBalance.getAccountBalance() - paymentDTO.getAmount() : paymentDTO.getAmount();
            accountBalance.setAccountBalance(newBalance);
            accountBalance.setProductCode(paymentDTO.getProductCode());
            accountBalance.setCurrencyCode(paymentDTO.getCurrencyCode());
            accountBalance.setBranchCode(paymentDTO.getBranchCode());
            AccountBalance savedAccountBalance = accountBalanceRepository.save(accountBalance);
            return objectMapper.convertValue(savedAccountBalance, AccountBalanceDTO.class);
        } catch (Exception e) {
            logger.error("Error saving account balance: {}", e.getMessage(), e);
            throw new InvalidRequestException("Error saving account balance: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_ACCOUNT, key = "#id")
    public AccountBalanceDTO findById(Long id) {
        Optional<AccountBalance> accountBalance = accountBalanceRepository.findById(id);
        return objectMapper.convertValue(accountBalance, AccountBalanceDTO.class);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_ACCOUNT, key = "#accountNumber")
    public AccountBalanceDTO findByAccountNumber(String accountNumber) {
        Optional<AccountBalance> accountBalance = accountBalanceRepository.findByAccountNumber(accountNumber);
        return objectMapper.convertValue(accountBalance, AccountBalanceDTO.class);
    }

    @Override
    @Cacheable(value = Constants.CACHE_NAME_ACCOUNT, key = "#internalAccountNumber")
    public AccountBalanceDTO findByInternalAccountNumber(Long internalAccountNumber) {
        Optional<AccountBalance> accountBalance = accountBalanceRepository.findByInternalAccountNumber(internalAccountNumber);
        return objectMapper.convertValue(accountBalance, AccountBalanceDTO.class);
    }

    @Override
    public double getAccountBalance(Long id) {
        Optional<AccountBalance> accountBalance = accountBalanceRepository.findById(id);
        if (accountBalance.isPresent()) {
            return accountBalance.get().getAccountBalance();
        }
        return 0;
    }


}
