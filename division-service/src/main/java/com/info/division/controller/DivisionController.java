package com.info.division.controller;

import java.net.URI;
import java.net.URISyntaxException;

import constant.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.division.model.Division;
import com.info.division.service.DivisionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.API_ENDPOINT + AppConstants.DIVISION)
@Tag(name = "RateLimiterController", description = "RateLimiter API Operations")
public class DivisionController {

   private final DivisionService divisionService;

   @PostMapping
   @Operation(description = "Create a Division.")
   public ResponseEntity<?> save(@RequestBody Division division) throws URISyntaxException {
      return ResponseEntity.created(new URI("/")).body(divisionService.save(division));
   }

   @GetMapping("/{id}")
   @Operation(description = "Find a Division By Id.")
   public ResponseEntity<?> findById(@PathVariable Long id) {
      return ResponseEntity.ok().body(divisionService.findById(id));
   }
}
