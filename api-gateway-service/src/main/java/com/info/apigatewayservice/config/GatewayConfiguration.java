package com.info.apigatewayservice.config;

import com.info.apigatewayservice.filter.AuthFilter;
import com.info.apigatewayservice.filter.PostGlobalFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;

import static com.info.apigatewayservice.constants.AppConstants.*;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfiguration.class);

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Department service route
                .route("DEPARTMENT-SERVICE", r -> r.path("/departments/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("department-cb")
                                        .setFallbackUri("forward:/departmentServiceFallBack")))
//                        .uri("http://localhost:8082"))
                        .uri("lb://DEPARTMENT-SERVICE"))

                .route("INSTANT-CASH-API-SERVICE", r -> r.path(API_ENDPOINT + INSTANT_CASH + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("").setFallbackUri("forward:/instantCashApiServiceFallBack")))
                        .uri("lb://INSTANT-CASH-API-SERVICE"))


                // User service route
                .route("USER-SERVICE", r -> r.path("/users/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("user-cb")
                                        .setFallbackUri("forward:/userServiceFallBack")))
                        .uri("lb://USER-SERVICE"))

                .route("BANK-SERVICE", r -> r.path(API_ENDPOINT + BANK + "/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c -> c.setName("").setFallbackUri("forward:/bankServiceFallBack")))
                        .uri("lb://BANK-SERVICE"))

                // Auth login route
                .route("AUTH-SERVICE", r -> r.path("/login")
                        .uri("http://localhost:8087")) // adjust for actual auth server

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
