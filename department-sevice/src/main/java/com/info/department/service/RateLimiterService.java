package com.info.department.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private int attempt = 1;

    @RateLimiter(name = "rateLimiterExample", fallbackMethod = "rateLimiterFallback")
    public String rateLimiterProcessRequest() {
        System.out.println("Attempt: " + attempt++);
        if (attempt <= 3) {
            throw new RuntimeException("Temporary issue");
        }
        return "Success";
    }

    // Fallback method
    public String rateLimiterFallback(Throwable t) {
        System.out.println( "Fallback response: " + t.getMessage());
        return "Fallback response: Too many requests.";
    }

    @RateLimiter(name = "myServiceRateLimiter", fallbackMethod = "rateLimitFallback")
    public String processRequest(String input) {
        // Simulate processing
        return "Processed: " + input;
    }

    public String rateLimitFallback(String input, Throwable t) {
        return "Rate limit exceeded. Please try again later.";
    }
}
