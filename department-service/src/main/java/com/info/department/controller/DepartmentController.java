package com.info.department.controller;

import com.info.department.annotation.APIDocumentation;
import com.info.department.model.Department;
import com.info.department.service.DepartmentService;
import com.info.dto.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.DEPARTMENT)
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
        logger.info("counter: {}, Round robin port: {}, department: {}", ++count, port, department);
        return ResponseEntity.ok().body(department);
    }

}
