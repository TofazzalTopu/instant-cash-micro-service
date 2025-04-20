package com.info.division.controller;

import java.net.URI;
import java.net.URISyntaxException;

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
@RequestMapping("/divisions")
public class DivisionController {

   private final DivisionService divisionService;

   @PostMapping
   public ResponseEntity<?> save(@RequestBody Division division) throws URISyntaxException {
      return ResponseEntity.created(new URI("/")).body(divisionService.save(division));
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> findById(@PathVariable Long id) {
      return ResponseEntity.ok().body(divisionService.findById(id));
   }
}
