package com.info.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.info.bank", "com.info.redis", "com.info.kafka"})
public class BankServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(BankServiceApplication.class, args);
   }

   @Bean
   @LoadBalanced
   public RestTemplate restTemplate() {
      return new RestTemplate();
   }

}
