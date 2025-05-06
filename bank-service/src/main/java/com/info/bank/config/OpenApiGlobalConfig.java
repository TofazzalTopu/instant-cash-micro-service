package com.info.bank.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiGlobalConfig {

    @Bean
    public OperationCustomizer globalResponsesCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiResponses responses = operation.getResponses();
            responses.addApiResponse("200", new ApiResponse().description("Success"));
            responses.addApiResponse("401", new ApiResponse().description("Unauthorized"));
            responses.addApiResponse("403", new ApiResponse().description("Forbidden"));
            responses.addApiResponse("404", new ApiResponse().description("Not Found"));
            return operation;
        };
    }
    @Bean
    public OperationCustomizer dynamicGlobalResponsesCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();

            // Clear existing responses if needed
            ApiResponses responses = operation.getResponses();

            // Common responses for all
            responses.addApiResponse("401", new ApiResponse().description("Unauthorized"));
            responses.addApiResponse("403", new ApiResponse().description("Forbidden"));

            // Add 404 only for GET methods
            if (operation.getOperationId().toLowerCase().startsWith("get")) {
                responses.addApiResponse("404", new ApiResponse().description("Not Found"));
            }

            // Custom rule: add 409 for Account-related controllers
            if (controllerName.contains("Account")) {
                responses.addApiResponse("409", new ApiResponse().description("Conflict (Account related)"));
            }

            // Check for custom annotations
//            if (handlerMethod.hasMethodAnnotation(YourCustomAnnotation.class)) {
//                responses.addApiResponse("422", new ApiResponse().description("Unprocessable Entity (Custom Rule)"));
//            }

            return operation;
        };
    }
}
