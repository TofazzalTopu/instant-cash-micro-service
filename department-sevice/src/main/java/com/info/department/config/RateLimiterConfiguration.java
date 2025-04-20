package com.info.department.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RateLimiterConfiguration {

//        @Autowired
//        private RateLimiterRegistry rateLimiterRegistry;
//
//        @Bean
//        public RateLimiter rateLimitWithCustomConfig() {
//            RateLimiterConfig customConfig = RateLimiterConfig.custom()
//                    .limitForPeriod(2)
//                    .limitRefreshPeriod(Duration.of(10, ChronoUnit.SECONDS))
//                    .timeoutDuration(Duration.of(5, ChronoUnit.SECONDS))
//                    .build();
//
//            return rateLimiterRegistry.rateLimiter("customRateLimiterConfig", customConfig);
//        }

}
