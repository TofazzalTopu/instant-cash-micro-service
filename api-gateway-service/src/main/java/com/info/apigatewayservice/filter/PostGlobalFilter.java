package com.info.apigatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.apigatewayservice.model.Company;
import com.info.apigatewayservice.model.Department;
import com.info.apigatewayservice.model.RemittanceData;
import com.info.apigatewayservice.model.Student;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

//@Component
public class PostGlobalFilter implements WebFilter {

    public static final Logger logger = LoggerFactory.getLogger(PostGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = getDecoratedResponse(path, response, request, dataBufferFactory);
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    private ServerHttpResponseDecorator getDecoratedResponse(String path, ServerHttpResponse response, ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {

            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);//MODIFY RESPONSE and Return the Modified response
                        System.out.println("requestId: " + request.getId() + ", method: " + request.getMethodValue() + ", req url: " + request.getURI() + ", request body :" + request);
                        logger.info("Response -> {}", responseBody);
                        try {
                            logger.info("RemittanceData responseBody: {}", new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(responseBody));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        logger.info("requestId: {}", request.getId() + ", method: " + request.getMethodValue() + ", req url: " + request.getURI() + ", response body :" + responseBody);
                        try {
                            if (request.getURI().getPath().equals("/apiservice/**") && request.getMethodValue().equals("GET")) {
//                                List<RemittanceData> remittanceDataList = new ObjectMapper().readValue(responseBody, List.class);
                                logger.info("responseBody: {}", new ObjectMapper().readValue(responseBody, List.class));
//                                logger.info("remittanceDataList: {}", remittanceDataList);
                            } else if (request.getURI().getPath().equals("/apiservice") && request.getMethodValue().equals("GET")) {
                                List<Department> departments = new ObjectMapper().readValue(responseBody, List.class);
                                logger.info("departments: {}", departments);
                            } else if (request.getURI().getPath().equals("/departments/id") && request.getMethodValue().equals("GET")) {
                                Department department = new ObjectMapper().readValue(responseBody, Department.class);
                                logger.info("departments: {}", department);
                            }
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        return dataBufferFactory.wrap(responseBody.getBytes());
                    })).onErrorResume(err -> {

                        System.out.println("error while decorating Response: {}" + err.getMessage());
                        return Mono.empty();
                    });

                }
                return super.writeWith(body);
            }
        };
    }

}
