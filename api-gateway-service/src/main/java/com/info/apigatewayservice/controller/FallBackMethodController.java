package com.info.apigatewayservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FallBackMethodController {
   private static final Logger logger = LoggerFactory.getLogger(FallBackMethodController.class);

   @GetMapping(value = "/userServiceFallBack")
   public String userServiceFallBackMethod(){
      logger.info("\nUser service is taking longer time then expected. Please try again later");
      return "User service is taking longer time then expected. Please try again later";
   }

   @GetMapping(value = "/instantCashApiServiceFallBack")
   public String instantCashApiServiceFallBackMethod(){
      logger.info("\nFallback response: InstantCashApi service is taking longer time then expected. Please try again later");
      return "Fallback response: InstantCashApi service is taking longer time then expected. Please try again later";
   }

//   @GetMapping("/fallback/instantcash")
//   public ResponseEntity<String> fallback(ServerHttpRequest request) {
//      logger.warn("Fallback triggered for path: {}", request.getPath());
//      return ResponseEntity.ok("Fallback response: INSTANT_CASH_API_SERVICE is currently unavailable. Please try again later.");
//   }

   @GetMapping(value = "/departmentServiceFallBack")
   public String departmentServiceFallBackMethod(){
      logger.info("\nDepartment service is taking longer time then expected. Please try again later");
      return "Department service is taking longer time then expected. Please try again later";
   }

   @GetMapping(value = "/bankServiceFallBack")
   public String bankServiceFallBackMethod(){
      logger.info("\nBank service is taking longer time then expected. Please try again later");
      return "Bank service is taking longer time then expected. Please try again later";
   }


}
