package com.info.transaction.service;

import com.info.transaction.feignclient.InstantCashServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cancelPaymentService")
@RequiredArgsConstructor
public class CancelPaymentService implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(CancelPaymentService.class.getName());

    private final InstantCashServiceFeignClient instantCashServiceFeignClient;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        logger.info("Cancelling payment for reference: {}", reference);
        String validationStatus = (String) execution.getVariable("validationStatus");
        String paymentStatus = (String) execution.getVariable("paymentStatus");

        if ("FAILED".equalsIgnoreCase(validationStatus)) {
            logger.info("================validation FAILED: {}, Payment with reference {} has been cancelled successfully.", validationStatus, reference);
            instantCashServiceFeignClient.updateRemittanceData(reference, "OPEN");
            execution.setVariable("cancelStatus", "CANCELLED");
        }
        if ("FAILED".equalsIgnoreCase(paymentStatus)) {
            logger.info("================Payment FAILED with reference {} has been cancelled successfully.", reference);
            instantCashServiceFeignClient.updateRemittanceData(reference, "OPEN");
            execution.setVariable("cancelStatus", "CANCELLED");
        }
    }
}

