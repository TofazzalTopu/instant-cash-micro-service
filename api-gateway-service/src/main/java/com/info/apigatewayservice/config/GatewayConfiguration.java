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

                .route("INSTANT-CASH-API-SERVICE", r -> r.path(INSTANT_CASH_WRITE + "/**")
                        .and().method("POST").filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("instant-cash-api-service-write-cb")
                                        .setFallbackUri("forward:/instantCashApiServiceFallBack")
//                                .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://INSTANT-CASH-API-SERVICE"))

                .route("INSTANT-CASH-API-READ-REPLICA-SERVICE", r -> r.path(INSTANT_CASH_READ + "/**")
                        .and().method("GET").filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("instant-cash-api-service-read-cb")
                                        .setFallbackUri("forward:/instantCashApiReadReplicaServiceFallBack")
//                                        .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://INSTANT-CASH-API-READ-REPLICA-SERVICE"))

                // User service route
                .route("USER-SERVICE", r -> r.path(USERS + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("user-service-cb")
                                        .setFallbackUri("forward:/userServiceFallBack")
//                                        .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://USER-SERVICE"))

                // Auth login route
                .route("AUTHENTICATION-SERVICE", r -> r.path(AUTH + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("authentication-service-cb")
                                        .setFallbackUri("forward:/authenticationServiceFallBack")
//                                .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://AUTHENTICATION-SERVICE"))

                .route("TRANSACTION-SERVICE", r -> r.path(TRANSACTION + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("transaction-service-cb")
                                        .setFallbackUri("forward:/transactionServiceFallBack")
//                                        .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://TRANSACTION-SERVICE"))

                .route("ACCOUNT-SERVICE", r -> r.path(ACCOUNTS + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("account-service-cb")
                                        .setFallbackUri("forward:/accountServiceFallBack")
//                                        .setStatusCodes(Set.of(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.GATEWAY_TIMEOUT.name()))
                                ))
                        .uri("lb://ACCOUNT-SERVICE"))


                .route("BANK-SERVICE", r -> r.path(BANK + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("bank-service-cb").setFallbackUri("forward:/bankServiceFallBack")))
                        .uri("lb://BANK-SERVICE"))

                // Department service route
                .route("DEPARTMENT-SERVICE", r -> r.path(DEPARTMENT + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("department-service-cb")
                                        .setFallbackUri("forward:/departmentServiceFallBack")))
                        .uri("lb://DEPARTMENT-SERVICE"))

                // Division service route
                .route("DIVISION-SERVICE", r -> r.path(DIVISION + "/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("division-service-cb")
                                        .setFallbackUri("forward:/divisionServiceFallBack")))
                        .uri("lb://DIVISION-SERVICE"))


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
