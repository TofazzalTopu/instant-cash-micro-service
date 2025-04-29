package com.info.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.info.api.processor.InstantCashAPIProcessor.process(..))")
    public Object logAroundInstantCashAPIProcessor(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Logging: InstantCash Remittance Scheduler started...");
        try {
            return joinPoint.proceed();
        } finally {
            logger.info("Logging: InstantCash Remittance Scheduler ended.");
        }
    }

    @Around("execution(* com.info.api.controller.*.*(..))")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        String requestStr = args != null ? objectMapper.writeValueAsString(args) : "";
        logger.info("Request Method: {}, Args: {}", methodName, requestStr);

        Object result = joinPoint.proceed();
        String responseStr = result != null ? objectMapper.writeValueAsString(result) : "";
        logger.info("Response from: {},  => {}", methodName, responseStr);
        return result;
    }

}

