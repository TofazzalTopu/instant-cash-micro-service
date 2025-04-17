package com.info.apigatewayservice.config;

import com.info.apigatewayservice.filter.AuthFilter;
import com.info.apigatewayservice.filter.PostGlobalFilter;
import com.info.apigatewayservice.filter.RequestFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;


//@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    public static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

//    private final RequestFilter requestFilter;
    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // adding 2 rotes to first microservice as we need to log request body if method is POST
        return builder.routes()
                .route("DEPARTMENT-SERVICE", r -> r.path("/departments/**")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c-> c.setName("").setFallbackUri("forward:/departmentServiceFallBack")))
                        .uri("http://localhost:8082"))
//                        .uri("lb://DEPARTMENT-SERVICE"))
//                .route("INSTANT_CASH_API_SERVICE", r -> r.path("/apiservice/**")
//                        .filters(f -> f.filters(authFilter)
//                                .circuitBreaker(c-> c.setName("").setFallbackUri("forward:/instantCashApiServiceFallBackMethod")))
//                        .uri("http://localhost:8081"))
//                        .uri("lb://INSTANT_CASH_API_SERVICE"))
                .route("INSTANT_CASH_API_SERVICE", r -> r.path("/apiservice/**")
                .filters(f -> f
                        .rewritePath("/apiservice/(?<segment>.*)", "/apiservice/${segment}")
//                        .filters(authFilter)
                        .circuitBreaker(c-> c.setName("instant-cash-cb").setFallbackUri("forward:/instantCashApiServiceFallBack")))
                        .uri("http://localhost:8081"))

//                .route("INSTANT_CASH_API_SERVICE", r -> r.path("/first")
//                        .and().method("POST")
//                        .and().readBody(RemittanceData.class, s -> true).filters(f -> f.filters(requestFilter, authFilter))
//                        .uri("lb://INSTANT_CASH_API_SERVICE"))
                .route("USER-SERVICE", r -> r.path("/users/*")
                        .filters(f -> f.filters(authFilter)
                                .circuitBreaker(c-> c.setName("").setFallbackUri("forward:/userServiceFallBack")))
                        .uri("lb://USER-SERVICE"))
                .route("auth-server", r -> r.path("/login")
                        .uri("http://localhost:8087"))
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebFilter responseFilter() {
        return new PostGlobalFilter();
    }

}
