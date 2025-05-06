package com.info.transaction.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("rollbackReferenceService")
public class RollbackReferenceService implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(RollbackReferenceService.class);

    @Override
    public void execute(DelegateExecution execution) {
        String reference = (String) execution.getVariable("reference");
        logger.info("Rolling back reference check for: {}", reference);
    }
}
