package com.info.department.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import com.info.department.annotation.APIDocumentation;
import com.info.department.constant.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.department.model.Department;
import com.info.department.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.API_ENDPOINT + AppConstants.DEPARTMENT)
@Tag(name = "Department APIs", description = "APIs for handling Department operations")
public class DepartmentController {
    public static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    @Value("${server.port}")
    String port;
    public int count = 0;


    @PostMapping
    @APIDocumentation
    @Operation(description = "Create a Department.")
    public ResponseEntity<Department> save(@RequestBody Department department) throws URISyntaxException {
        Department department1 = departmentService.save(department);
        logger.info("Department saved: {}", department1);
        return ResponseEntity.created(new URI("/")).body(department1);
    }

    @GetMapping("/{id}")
    @APIDocumentation
    @Operation(description = "Get Department By Id.")
    public ResponseEntity<Department> findById(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (Objects.isNull(department)) department = new Department(id, "Math");
        logger.info("counter: {}", ++count);
        logger.info("Round robin port: {}", port);
        log.info("department: {}", department);
        return ResponseEntity.ok().body(department);
    }

}
