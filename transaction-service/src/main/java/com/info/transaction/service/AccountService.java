package com.info.transaction.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("accountService")
public class AccountService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String accountNumber = (String) execution.getVariable("accountNumber");
        System.out.println("AccountService for account number: " + accountNumber);
    }
}
