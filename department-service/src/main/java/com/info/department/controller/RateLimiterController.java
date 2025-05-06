package com.info.department.controller;

import com.info.department.annotation.GetAPIDocumentation;
import com.info.dto.constants.Constants;

import com.info.department.service.RateLimiterService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(Constants.DEPARTMENT)
@Tag(name = "RateLimiterController", description = "RateLimiter API Operations")
public class RateLimiterController {

    @Autowired private RateLimiterService rateLimiterService;


    @GetAPIDocumentation
    @GetMapping(value = "/myRateLimiter/{input}")
    @Operation(summary = "Rate Limiter Example With Fallback.")
    @RateLimiter(name = "rateLimiterExample", fallbackMethod = "rateLimiterExampleFallback")
    public ResponseEntity<String> myRateLimiter(@PathVariable String input) {
        return ResponseEntity.ok("Processed: " + input);
    }

    @GetAPIDocumentation
    @GetMapping(value = "/rateLimiter")
    @Operation(summary = "Rate Limiter Example With Fallback.")
    public ResponseEntity<?> rateLimiterProcessRequest() {
        return ResponseEntity.ok().body(rateLimiterService.rateLimiterProcessRequest());
    }


    // Fallback method for myRateLimiter()
    public ResponseEntity<String> rateLimiterExampleFallback(String input, Throwable t) {
        return ResponseEntity.status(429).body("Rate limit exceeded. Please try again later.");
    }
}

