package com.info.division.controller;

import com.info.division.annotation.APIDocumentation;
import com.info.dto.constants.Constants;
import com.info.division.model.Division;
import com.info.division.service.DivisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.DIVISION)
@Tag(name = "DivisionController", description = "Division API Operations")
public class DivisionController {

    private final DivisionService divisionService;

    @Value("${server.port}")
    String port;


    @PostMapping
    @APIDocumentation
    @Operation(description = "Create a Division.")
    public ResponseEntity<Division> save(@RequestBody Division division) throws URISyntaxException {
        log.info("division: {}", division);
        return ResponseEntity.created(new URI("/")).body(divisionService.save(division));
    }

    @GetMapping("/{id}")
    @APIDocumentation
    @Operation(description = "Find a Division By Id.")
    public ResponseEntity<Division> findById(@PathVariable Long id) {
        log.info("division id: {}, port: {}", id, port);
        return ResponseEntity.ok().body(divisionService.findById(id));
    }
}
