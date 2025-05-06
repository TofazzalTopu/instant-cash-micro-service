package com.info.transaction.service;

import com.info.dto.account.PaymentDTO;
import com.info.transaction.feignclient.AccountServiceFeignClient;
import com.info.transaction.feignclient.InstantCashServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("handleFinalizeErrorService")
public class HandleFinalizeErrorService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HandleFinalizeErrorService.class.getName());
    private final InstantCashServiceFeignClient instantCashServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String reference = (String) delegateExecution.getVariable("reference");
        logger.info("Compensating payment for reference: {}", reference);
        String paymentStatus = (String) delegateExecution.getVariable("paymentStatus");
        String validationStatus = (String) delegateExecution.getVariable("validationStatus");
        String accountNumber = (String) delegateExecution.getVariable("accountNumber");
        PaymentDTO paymentDTO = (PaymentDTO) delegateExecution.getVariable("paymentDTO");

        // Calling a reversal or refund API here
        if ("FAILED".equalsIgnoreCase(paymentStatus)) {
            logger.info("Payment failed for account number: {}", accountNumber);
            logger.info("================Payment FAILED: {}, Payment with reference {} has been cancelled successfully.", validationStatus, reference);
            logger.info("Reverting amount for account number: {}", accountNumber);
            accountServiceFeignClient.revertAmount(paymentDTO);

            logger.info("Updating remittance status for reference: {}", reference);
            instantCashServiceFeignClient.updateRemittanceData(reference, "OPEN");
            delegateExecution.setVariable("cancelStatus", "CANCELLED");
        }


    }
}
