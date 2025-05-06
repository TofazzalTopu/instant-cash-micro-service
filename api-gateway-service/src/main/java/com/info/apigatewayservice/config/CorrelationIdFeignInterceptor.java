package com.info.apigatewayservice.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import static com.info.dto.constants.Constants.CORRELATION_ID;


@Component
public class CorrelationIdFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String correlationId = MDC.get(CORRELATION_ID);
        if (correlationId != null) {
            template.header(CORRELATION_ID, correlationId);
        }
    }
}

