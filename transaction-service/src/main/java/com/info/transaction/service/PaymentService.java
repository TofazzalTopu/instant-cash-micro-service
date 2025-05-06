package com.info.transaction.service;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("paymentService")
@RequiredArgsConstructor
public class PaymentService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class.getName());

    private final AccountService accountService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        String accountNumber = (String) execution.getVariable("accountNumber");
        logger.info("Transaction Posting for reference: {} accountNumber: {}", reference, accountNumber);

        PaymentDTO paymentDTO = (PaymentDTO) execution.getVariable("paymentDTO");
        String userId = (String) execution.getVariable("userId");


        AccountBalanceDTO accountBalanceDTO = accountService.updateAccountBalance(reference, userId, paymentDTO, execution);

    }
}
