package com.info.userservice.controller;

import com.info.userservice.annotation.APIDocumentation;
import com.info.userservice.constants.AppConstants;
import com.info.userservice.dto.CollectionAPIResponse;
import com.info.userservice.dto.RoutingNumberDTO;
import com.info.userservice.entity.Branch;
import com.info.userservice.service.BranchService;
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
@RequestMapping(AppConstants.API_ENDPOINT + AppConstants.BANK + AppConstants.BRANCH)
@Tag(name = "Branch APIs", description = "APIs for handling Branch operations")
public class BranchController {

    private final BranchService branchService;

    @Value("${server.port}")
    String port;

    @GetMapping
    @APIDocumentation
    @Operation(description = "List of all Branch.")
    public ResponseEntity<CollectionAPIResponse<Branch>> findAll() {
        List<Branch> branchs = branchService.findAll();
        if (branchs.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CollectionAPIResponse<>(branchService.findAll()));
    }

    @PostMapping
    @APIDocumentation
    @Operation(description = "Create an Branch.")
    public ResponseEntity<?> save(@RequestBody Branch branch) throws URISyntaxException {
        log.info("Request Branch body: {}", branch);
        return ResponseEntity.created(new URI("/")).body(branchService.save(branch));
    }

    @APIDocumentation
    @GetMapping(value = "/{id}")
    @Operation(description = "Find an Branch by id.")
    public ResponseEntity<Branch> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(branchService.findById(id));
    }

    @APIDocumentation
    @PostMapping(value = "/by-routing-numbers")
    @Operation(description = "Find an Branch By Routing Numbers.")
    public ResponseEntity<?> findBranchByRoutingNumbers(@RequestBody RoutingNumberDTO routingNumberDTO) {
        return ResponseEntity.ok().body(branchService.findAllByRoutingNumber(routingNumberDTO.getRoutingNumbers()));
    }


}
