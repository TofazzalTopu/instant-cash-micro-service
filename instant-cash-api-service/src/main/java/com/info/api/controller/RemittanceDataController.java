package com.info.api.controller;

import com.info.api.annotation.GetAPIDocumentation;
import com.info.api.annotation.PostApiDocumentation;
import com.info.api.service.common.RemittanceDataService;
import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.INSTANT_CASH_WRITE)
@Tag(name = "Instant Cash", description = "APIs for handling Instant Cash remittance operations")
public class RemittanceDataController {

    private final RemittanceDataService remittanceDataService;

    @GetAPIDocumentation
    @GetMapping("/remittance/{reference}")
    @Operation(summary = "Find Remittance by reference.")
    public ResponseEntity<RemittanceDataDTO> findByReferenceNo(@PathVariable String reference) {
        Optional<RemittanceDataDTO> remittanceData = remittanceDataService.saveAndRemittanceData(reference);
        return remittanceData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }
    @PostApiDocumentation
    @PostMapping("/update")
    @Operation(summary = "Update remittance by reference.")
    public ResponseEntity<RemittanceDataDTO> updateRemittanceData(@RequestParam String reference, @RequestParam String status) {
        Optional<RemittanceDataDTO> remittanceData = remittanceDataService.updateRemittanceData(reference, status);
        return remittanceData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

}
