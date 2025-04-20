package com.info.department.controller;


import com.info.department.service.RetryService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/departments")
public class RetryController {

    @Autowired
    private RetryService retryService;
    private int attempt = 1;

    //Approach -1
    @GetMapping("/retry")
    public ResponseEntity<?> retry() {
        return ResponseEntity.ok().body(retryService.retryProcessRequest());
    }

    //Approach -2
    @GetMapping("/retryRequest")
    @Retry(name = "retryExample", fallbackMethod = "myApiRetryFallback")
    public ResponseEntity<?> retryRequest() {
        System.out.println("Attempt: " + attempt++);
        if (attempt <= 2) {
            throw new RuntimeException("Temporary issue");
        }
        return ResponseEntity.ok().body("success");
    }

    // Fallback method for myRateLimiter()
    public ResponseEntity<String> myApiRetryFallback(Throwable t) {
        return ResponseEntity.status(409).body("Fallback response: " + t.getMessage());
    }
}

