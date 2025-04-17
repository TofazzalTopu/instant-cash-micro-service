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

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

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

                // Instant Cash API service route
                .route("INSTANT_CASH_API_SERVICE", r -> r.path("/apiservice/**")
                        .filters(f -> f
                                .rewritePath("/apiservice/(?<segment>.*)", "/apiservice/${segment}")
//                                .rewritePath("/apiservice/(?<segment>.*)", "/${segment}")
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("instant-cash-cb")
                                        .setFallbackUri("forward:/instantCashApiServiceFallBack")))
//                        .uri("lb://INSTANT_CASH_API_SERVICE"))
                        .uri("http://localhost:8081"))  // adjust the port or use lb://INSTANT_CASH_API_SERVICE

                // User service route
                .route("USER-SERVICE", r -> r.path("/users/**")
                        .filters(f -> f
                                .filters(authFilter)
                                .circuitBreaker(c -> c
                                        .setName("user-cb")
                                        .setFallbackUri("forward:/userServiceFallBack")))
                        .uri("http://localhost:8083")) // adjust the port or use lb://USER-SERVICE

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
