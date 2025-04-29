package com.info.apigatewayservice.config;

import com.info.apigatewayservice.filter.AuthFilter;
import com.info.apigatewayservice.filter.PostGlobalFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;

import static com.info.dto.constants.Constants.*;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Department service route
                .route("DEPARTMENT-SERVICE", r -> r.path(DEPARTMENT + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("department-cb")
                                        .setFallbackUri("forward:/departmentServiceFallBack")))
                        .uri("lb://DEPARTMENT-SERVICE"))

                // Division service route
                .route("DIVISION-SERVICE", r -> r.path(DIVISION + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("division-cb")
                                        .setFallbackUri("forward:/divisionServiceFallBack")))
                        .uri("lb://DIVISION-SERVICE"))

                .route("INSTANT-CASH-API-SERVICE", r -> r.path(INSTANT_CASH + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("").setFallbackUri("forward:/instantCashApiServiceFallBack")))
                        .uri("lb://INSTANT-CASH-API-SERVICE"))

                // User service route
                .route("USER-SERVICE", r -> r.path(USERS + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("user-cb")
                                        .setFallbackUri("forward:/userServiceFallBack")))
                        .uri("lb://USER-SERVICE"))

                .route("BANK-SERVICE", r -> r.path(BANK + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("").setFallbackUri("forward:/bankServiceFallBack")))
                        .uri("lb://BANK-SERVICE"))

                // Auth login route
                .route("AUTHENTICATION-SERVICE", r -> r.path(AUTH + "/**")
                        .uri("lb://AUTHENTICATION-SERVICE"))

                .build();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebFilter responseFilter() {
        return new PostGlobalFilter(); // optional post-filter
    }
}
