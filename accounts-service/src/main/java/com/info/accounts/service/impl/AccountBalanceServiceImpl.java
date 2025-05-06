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
            accountBalance.setEntityNumber(1);
            accountBalance.setAmountOnHold(0.0);
            accountBalance.setAccountBalance(newBalance);
            accountBalance.setAvailableBalance(newBalance);
            accountBalance.setBranchCode(paymentDTO.getBranchCode());
            accountBalance.setProductCode(paymentDTO.getProductCode());
            accountBalance.setCurrencyCode(paymentDTO.getCurrencyCode());
            accountBalance.setAccountNumber(paymentDTO.getAccountNumber());
            accountBalance.setInternalAccountNumber(Long.parseLong(paymentDTO.getAccountNumber()));
            AccountBalance savedAccountBalance = accountBalanceRepository.save(accountBalance);
            logger.info("Account balance saved successfully: {}", savedAccountBalance);
            return objectMapper.convertValue(savedAccountBalance, AccountBalanceDTO.class);
        } catch (Exception e) {
            logger.error("Error saving account balance: {}", e.getMessage(), e);
            throw new InvalidRequestException("Error saving account balance: " + e.getMessage());
        }
    }

    @Override
    public AccountBalanceDTO revertAmount(PaymentDTO paymentDTO) {
        try {
            if (paymentDTO == null || paymentDTO.getAccountNumber() == null || Objects.isNull(paymentDTO.getAmount())) {
                throw new InvalidRequestException("Amount must not be null");
            }

            Optional<AccountBalance> optionalAccountBalance = accountBalanceRepository.findByAccountNumber(paymentDTO.getAccountNumber());
            if (optionalAccountBalance.isPresent()) {
                AccountBalance accountBalance = optionalAccountBalance.get();
                double newBalance = Objects.nonNull(accountBalance.getAccountBalance()) ? accountBalance.getAccountBalance() + paymentDTO.getAmount() : paymentDTO.getAmount();
                double holdBalance = Objects.nonNull(accountBalance.getAmountOnHold()) ? accountBalance.getAmountOnHold() - paymentDTO.getAmount() : paymentDTO.getAmount();
                accountBalance.setAccountBalance(newBalance);
                accountBalance.setAvailableBalance(newBalance);
                accountBalance.setAmountOnHold(holdBalance);
                accountBalance.setBranchCode(paymentDTO.getBranchCode());
                accountBalance.setAccountNumber(paymentDTO.getAccountNumber());
                accountBalance.setInternalAccountNumber(Long.parseLong(paymentDTO.getAccountNumber()));
                accountBalanceRepository.save(accountBalance);
                return objectMapper.convertValue(accountBalance, AccountBalanceDTO.class);
            } else {
                throw new InvalidRequestException("Account not found");
            }
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
