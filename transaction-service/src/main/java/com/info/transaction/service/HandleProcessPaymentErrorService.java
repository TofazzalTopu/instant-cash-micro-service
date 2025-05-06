package com.info.transaction.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service("handleProcessPaymentErrorService")
public class HandleProcessPaymentErrorService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HandleProcessPaymentErrorService.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String reference = (String) delegateExecution.getVariable("reference");
        String errorMessage = (String) delegateExecution.getVariable("errorMessage");
        logger.info("Compensating payment for reference: {}, Error message: {}", reference, errorMessage);

    }
}
