package com.info.department.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

    private int attempt = 1;

    @Retry(name = "retryExample", fallbackMethod = "retryFallback")
    public String retryProcessRequest() {
        System.out.println("Attempt: " + attempt++);
        if (attempt <= 2) {
            throw new RuntimeException("Temporary issue");
        }
        return "Success";
    }

    // Fallback method for retryProcessRequest() method
    public String retryFallback(Throwable t) {
        System.out.println( "Fallback response: " + t.getMessage());
        return "Fallback response: " + t.getMessage();
    }


}
