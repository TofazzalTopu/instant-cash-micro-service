package com.info.apigatewayservice.util;

import com.info.apigatewayservice.model.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthUtil {
    public static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    private AuthUtil() {
    }

    @Autowired
    private RestTemplate restTemplate;
    protected static String tokenUrl = "http://localhost:8080/auth/token";

    public String getToken(String userName, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userName",userName);
        headers.set("role",role);
        HttpEntity<Credential> request = new HttpEntity<>(new Credential(userName, role), headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST,request,String.class);
        logger.info("token: {}", response.getBody());
        return response.getBody();
    }
}
