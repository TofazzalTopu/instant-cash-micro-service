package com.info.api.schedular;

import com.info.api.processor.InstantCashAPIProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class ApiClientScheduler {

    public static final Logger logger = LoggerFactory.getLogger(ApiClientScheduler.class);

    private final InstantCashAPIProcessor instantCashAPIProcessor;

    public ApiClientScheduler(InstantCashAPIProcessor instantCashAPIProcessor) {
        this.instantCashAPIProcessor = instantCashAPIProcessor;
    }

    @Value("${IS_INSTANT_CASH_API_SCHEDULER_ENABLED:true}")
    boolean isICSchedulerEnabled;

//    @Scheduled(fixedDelayString = "${API_SCHEDULER_INTERVAL}", initialDelay = 1000)
    public void apiClientScheduler() {
        ExecutorService instantCashExecutor = Executors.newFixedThreadPool(1);
        try {
            if (isICSchedulerEnabled) instantCashExecutor.execute(instantCashAPIProcessor::process);
        } finally {
            while (!instantCashExecutor.isTerminated()) {
            }
            instantCashExecutor.shutdown();
        }
    }

}
