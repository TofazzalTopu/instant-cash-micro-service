package com.info.bank.controller;

import com.info.bank.annotation.GetAPIDocumentation;
import com.info.bank.annotation.PostApiDocumentation;
import com.info.bank.dto.CollectionAPIResponse;
import com.info.bank.entity.User;
import com.info.bank.service.UserService;
import com.info.dto.constants.Constants;
import com.info.dto.department.UserDepartmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.USERS)
@Tag(name = "User APIs", description = "APIs for handling User operations")
public class UserController {

    private final UserService userService;

    @Value("${server.port}")
    String port;

    @GetMapping
    @GetAPIDocumentation
    @Operation(summary = "List of all user.")
    public ResponseEntity<CollectionAPIResponse<User>> findAll() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CollectionAPIResponse<>(userService.findAll()));
    }

    @PostMapping
    @PostApiDocumentation
    @Operation(summary = "Create an user.")
    public ResponseEntity<User> save(@RequestBody User user) throws URISyntaxException {
        log.info("Request user body: {}", user);
        return ResponseEntity.created(new URI("/")).body(userService.save(user));
    }

    @GetAPIDocumentation
    @GetMapping(value = "/{id}")
    @Operation(summary = "Find an user by id.")
    public ResponseEntity<UserDepartmentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserAndDepartmentByIdWithRestApi(id));
    }

    @GetAPIDocumentation
    @GetMapping(value = "/{id}/feign-client")
    @Operation(summary = "Find an user by id using feign client.")
    public ResponseEntity<UserDepartmentDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserAndDepartmentById(id));
    }


}
