package com.info.api.controller;

import com.info.api.annotation.APIDocumentation;
import com.info.api.dto.ic.APIResponse;
import com.info.api.entity.RemittanceData;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.util.ApiUtil;
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
@RequestMapping(Constants.INSTANT_CASH)
@Tag(name = "Instant Cash", description = "APIs for handling Instant Cash remittance operations")
public class RemittanceDataController {

    private final RemittanceDataService remittanceDataService;

    @GetMapping("/remittance/{reference}")
    @Operation(description = "Find Remittance by reference.")
    public ResponseEntity<RemittanceDataDTO> findByReferenceNo(@PathVariable String reference) {
        Optional<RemittanceDataDTO> remittanceData = remittanceDataService.findBydReferenceNo(reference);
        return remittanceData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

}
