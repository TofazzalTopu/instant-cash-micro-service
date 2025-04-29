package com.info.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.info.api", "com.info.redis", "com.info.kafka"})
public class InstantCashApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstantCashApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(5000);  // connection timeout (ms)
		factory.setReadTimeout(5000);     // read timeout (ms)
		return new RestTemplate(factory);
	}

}
