package com.info.user.controller;

import com.info.dto.constants.Constants;
import com.info.dto.department.DepartmentDTO;
import com.info.dto.department.UserDepartmentDTO;
import com.info.dto.user.UserDTO;
import com.info.user.feignClient.DepartmentServiceFeignClient;
import com.info.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Tag(name = "User APIs", description = "User API Operations")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;
    private final DepartmentServiceFeignClient departmentServiceFeignClient;

    @Value("${server.port}")
    String port;

    @GetMapping
    public String success() {
        return "Success response from user service";
    }

    @PostMapping
    @Operation(description = "Create user.")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user) throws URISyntaxException {
        log.info("Request user body: {}", user);
        return ResponseEntity.created(new URI(Constants.USERS)).body(userService.save(user));
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Find user by id.")
    public ResponseEntity<UserDepartmentDTO> findById(@PathVariable Long id) {
        logger.info("port: {}", port);
        UserDTO userDTO = userService.findById(id);
        log.info("user: " + userDTO);

        DepartmentDTO department = departmentServiceFeignClient.findById(userDTO.getDepartmentId());
        return ResponseEntity.ok().body(new UserDepartmentDTO(userDTO, department));
    }

}
