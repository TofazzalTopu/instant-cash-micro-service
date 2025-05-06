package com.info.transaction.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private RuntimeService runtimeService;

    public void createOrder(String orderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);

        runtimeService.startProcessInstanceByKey("orderSagaProcess", variables);
    }
}
