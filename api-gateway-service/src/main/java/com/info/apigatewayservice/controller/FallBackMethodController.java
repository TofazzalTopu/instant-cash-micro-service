package com.info.apigatewayservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FallBackMethodController {
   private static final Logger logger = LoggerFactory.getLogger(FallBackMethodController.class);

   @GetMapping(value = "/authenticationServiceFallBack")
   public String authenticationServiceFallBack(){
      logger.info("\nAuthentication service is taking longer time then expected. Please try again later");
      return "Authentication service is taking longer time then expected. Please try again later";
   }
   @GetMapping(value = "/transactionServiceFallBack")
   public String transactionServiceFallBack(){
      logger.info("\nTransaction service is taking longer time then expected. Please try again later");
      return "Transaction service is taking longer time then expected. Please try again later";
   }

   @GetMapping(value = "/accountServiceFallBack")
   public String accountServiceFallBack(){
      logger.info("\nAccount service is taking longer time then expected. Please try again later");
      return "Account service is taking longer time then expected. Please try again later";
   }

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


   @GetMapping(value = "/instantCashApiReadReplicaServiceFallBack")
   public String instantCashApiReadReplicaServiceFallBack(){
      logger.info("\nFallback response: InstantCashApi service is taking longer time then expected. Please try again later");
      return "Fallback response: InstantCashApi service is taking longer time then expected. Please try again later";
   }


   @GetMapping(value = "/bankServiceFallBack")
   public String bankServiceFallBackMethod(){
      logger.info("\nBank service is taking longer time then expected. Please try again later");
      return "Bank service is taking longer time then expected. Please try again later";
   }


   @GetMapping(value = "/departmentServiceFallBack")
   public String departmentServiceFallBackMethod(){
      logger.info("\nDepartment service is taking longer time then expected. Please try again later");
      return "Department service is taking longer time then expected. Please try again later";
   }

   @GetMapping(value = "/divisionServiceFallBack")
   public String divisionServiceFallBackMethod(){
      logger.info("\nDivision service is taking longer time then expected. Please try again later");
      return "Division service is taking longer time then expected. Please try again later";
   }


}
