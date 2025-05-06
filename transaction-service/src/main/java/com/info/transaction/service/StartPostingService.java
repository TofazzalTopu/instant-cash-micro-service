package com.info.transaction.service;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StartPostingService {
    private static final Logger logger = LoggerFactory.getLogger(StartPostingService.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    public void postPayment(String reference) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("reference", reference);
        logger.info("Starting transaction posting process with reference: {}", reference);
        runtimeService.startProcessInstanceByKey("transactionPostingProcess", variables);
    }
}
