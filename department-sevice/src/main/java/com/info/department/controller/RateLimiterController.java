package com.info.department.controller;


import com.info.department.service.RateLimiterService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/departments")
public class RateLimiterController {

    @Autowired private RateLimiterService rateLimiterService;


    @GetMapping(value = "/myRateLimiter/{input}")
    @RateLimiter(name = "rateLimiterExample", fallbackMethod = "rateLimiterExampleFallback")
    public ResponseEntity<String> myRateLimiter(@PathVariable String input) {
        return ResponseEntity.ok("Processed: " + input);
    }

    @GetMapping(value = "/rateLimiter")
    public ResponseEntity<?> rateLimiterProcessRequest() {
        return ResponseEntity.ok().body(rateLimiterService.rateLimiterProcessRequest());
    }


    // Fallback method for myRateLimiter()
    public ResponseEntity<String> rateLimiterExampleFallback(String input, Throwable t) {
        return ResponseEntity.status(429).body("Rate limit exceeded. Please try again later.");
    }
}

