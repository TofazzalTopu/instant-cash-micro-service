package com.info.department.config;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RateLimitEventConfig {

    @Autowired
    private RateLimiterRegistry registry;


//    @RateLimiter(name = "rateLimiterEventsExample")
//    public String getMovieDetailsWithRateLimiterEventDetails(String movieId) {
//        return "Success";
//    }


    @PostConstruct
    public void postConstruct() {
        io.github.resilience4j.ratelimiter.RateLimiter.EventPublisher eventPublisher = registry.rateLimiter("rateLimiterEventsExample").getEventPublisher();
        eventPublisher.onEvent(event -> System.out.println("Simple Rate Limit - On Event. Event Details: " + event));
        eventPublisher.onSuccess(event -> System.out.println("Simple Rate Limit - On Success. Event Details: " + event));
        eventPublisher.onFailure(event -> System.out.println("Simple Rate Limit - On Failure. Event Details: " + event));
    }
}
