package com.info.transaction.service;

import com.info.transaction.feignclient.InstantCashServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("handleValidationErrorService")
public class HandleValidationErrorService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HandleValidationErrorService.class.getName());
    private final InstantCashServiceFeignClient instantCashServiceFeignClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String reference = (String) delegateExecution.getVariable("reference");
        String errorMessage = (String) delegateExecution.getVariable("errorMessage");
        logger.info("HandleValidationError: Cancelling payment for reference: {}", reference);
        String validationStatus = (String) delegateExecution.getVariable("validationStatus");
        logger.info("Compensating ValidationError: Error message: {}", errorMessage);

        // Calling a reversal or refund API here
        if ("FAILED".equalsIgnoreCase(validationStatus)) {
            logger.info("================validation FAILED: {}, Payment with reference {} has been cancelled successfully.", validationStatus, reference);
            instantCashServiceFeignClient.updateRemittanceData(reference, "OPEN");
            delegateExecution.setVariable("cancelStatus", "CANCELLED");
        }

    }
}
