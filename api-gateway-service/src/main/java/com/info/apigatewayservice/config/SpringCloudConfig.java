package com.info.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

//   @Bean
//   public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//      return builder.routes().route(r -> r.path("/departments/**").uri("lb://DEPARTMENT-SERVICE")).
//                    .route(r -> r.path("/users/**").uri("lb://USER-SERVICE")).build();
//   }

//   @Bean
//   public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//      return builder.routes()
//                    .route(r -> r.path("/employee/**")
//                                 .uri("lb://FIRST-SERVICE")
//                                 .id("employeeModule"))
//
//                    .route(r -> r.path("/consumer/**")
//                                 .uri("lb://SECOND-SERVICE")
//                                 .id("consumerModule"))
//                    .build();

      // return builder.routes()
      // .route(r -> r.path("/employee/**")
      // .uri("http://localhost:8081/")
      // .id("employeeModule"))
      //
      // .route(r -> r.path("/consumer/**")
      // .uri("http://localhost:8082/")
      // .id("consumerModule"))
      // .build();
//   }

}