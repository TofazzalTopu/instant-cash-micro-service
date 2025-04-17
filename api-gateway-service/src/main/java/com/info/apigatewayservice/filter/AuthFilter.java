package com.info.apigatewayservice.filter;

import com.info.apigatewayservice.util.AuthUtil;
import com.info.apigatewayservice.util.JWTUtil;
import com.info.apigatewayservice.validator.RouteValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    public static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private static final String USER_NAME = "userName";
    private static final String USER_ROLE = "role";
    private static final String AUTHORIZATION = "Authorization";

    private final RouteValidator routeValidator;
    private final JWTUtil jwtUtil;
    private final AuthUtil authUtil;

    @Value("${authentication.enabled}")
    private boolean authEnabled;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!authEnabled) {
//            logger.info("Authentication is disabled. To enable it, make \"authentication.enabled\" property as true");
            return chain.filter(exchange);
        }
        String token = "";
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {
            logger.info("validating authentication token");
            if (this.isCredsMissing(request)) {
                logger.error("Credentials missing- {}", HttpStatus.UNAUTHORIZED);
                return this.onError(exchange, "Credentials missing", HttpStatus.UNAUTHORIZED);
            }
            if (request.getHeaders().containsKey(USER_NAME) && request.getHeaders().containsKey(USER_ROLE)) {
                token = authUtil.getToken(request.getHeaders().get(USER_NAME).toString(), request.getHeaders().get(USER_ROLE).toString());
            } else {
                token = request.getHeaders().get(AUTHORIZATION).toString().split(" ")[1];
            }

            if (jwtUtil.isInvalid(token)) {
                logger.error(" Auth header invalid - {}", HttpStatus.UNAUTHORIZED);
                return this.onError(exchange, "Auth header invalid", HttpStatus.UNAUTHORIZED);
            } else {
                logger.info("Authentication is successful for the user: {}, Success - {}", HttpStatus.OK, request.getHeaders().get(USER_NAME));
            }

            this.populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION).get(0);
    }


    private boolean isCredsMissing(ServerHttpRequest request) {
        return !(request.getHeaders().containsKey(USER_NAME) && request.getHeaders().containsKey(USER_ROLE)) && !request.getHeaders().containsKey(AUTHORIZATION);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getALlClaims(token);
        exchange.getRequest()
                .mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
