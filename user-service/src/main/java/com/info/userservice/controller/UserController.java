package com.info.userservice.controller;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.info.userservice.dto.Department;
import com.info.userservice.dto.UserDTO;
import com.info.userservice.feignClient.FeignClientDepartmentService;
import com.info.userservice.model.User;
import com.info.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

   private final UserService userService;
   private final FeignClientDepartmentService feignClientDepartmentService;
   private final RestTemplate restTemplate;

   @Value("${server.port}")
   String port;

   @GetMapping
   public String success() {
      return "Success response from user service";
   }

   @PostMapping
   public ResponseEntity<?> save(@RequestBody User user) throws URISyntaxException {
      log.info("Request user body: " + user);
      return ResponseEntity.created(new URI("/")).body(userService.save(user));
   }

   @GetMapping(value = "/{id}")
   public ResponseEntity<?> findById(@PathVariable Long id) {
      System.out.println("port: " + port);
      User user = userService.findById(id);
      log.info("user: " + user);

//      Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+ user.getDepartmentId(), Department.class);
      Department department = feignClientDepartmentService.findById(user.getDepartmentId());
      return ResponseEntity.ok().body(new UserDTO(user, department));
   }

}
