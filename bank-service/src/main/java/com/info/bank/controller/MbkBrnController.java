package com.info.bank.controller;

import com.info.bank.annotation.GetAPIDocumentation;
import com.info.bank.annotation.PostApiDocumentation;
import com.info.bank.dto.CollectionAPIResponse;
import com.info.bank.dto.RoutingNumberDTO;
import com.info.bank.entity.MbkBrn;
import com.info.bank.entity.MbkBrnKey;
import com.info.bank.service.MbkBrnService;
import com.info.dto.constants.Constants;
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
@RequestMapping(Constants.BANK + Constants.MBK_BRN)
@Tag(name = "Mbk Branch APIs", description = "APIs for handling Mbk Branch operations")
public class MbkBrnController {

    private final MbkBrnService mbkBrnService;

    @Value("${server.port}")
    String port;

    @GetMapping
    @GetAPIDocumentation
    @Operation(summary = "List of all MbkBrn.")
    public ResponseEntity<CollectionAPIResponse<MbkBrn>> findAll() {
        List<MbkBrn> branchs = mbkBrnService.findAll();
        if (branchs.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CollectionAPIResponse<>(mbkBrnService.findAll()));
    }

    @PostMapping
    @PostApiDocumentation
    @Operation(summary = "Create an MbkBrn.")
    public ResponseEntity<?> save(@RequestBody MbkBrn mbkBrn) throws URISyntaxException {
        log.info("Request MbkBrn body: {}", mbkBrn);
        return ResponseEntity.created(new URI("/")).body(mbkBrnService.save(mbkBrn));
    }

    @PostApiDocumentation
    @PostMapping(value = "/mbkBrnKey")
    @Operation(summary = "Find an MbkBrn by id.")
    public ResponseEntity<MbkBrn> findById(@RequestBody MbkBrnKey mbkBrnKey) {
        return ResponseEntity.ok().body(mbkBrnService.findById(mbkBrnKey));
    }

    @PostApiDocumentation
    @PostMapping(value = "/by-routing-numbers")
    @Operation(summary = "Find an MbkBrn By Routing Numbers.")
    public ResponseEntity<?> findMbkBrnByRoutingNumbers(@RequestBody RoutingNumberDTO routingNumberDTO) {
        return ResponseEntity.ok().body(mbkBrnService.findAllByMbkbrnKeyBranchRoutingIn(routingNumberDTO.getRoutingNumbers()));
    }


}
