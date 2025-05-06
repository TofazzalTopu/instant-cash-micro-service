package com.info.api.controller;

import com.info.api.annotation.GetAPIDocumentation;
import com.info.api.service.common.RemittanceDataService;
import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.INSTANT_CASH_READ)
@Tag(name = "Instant Cash", description = "APIs for handling Instant Cash remittance operations")
public class RemittanceDataController {

    private final RemittanceDataService remittanceDataService;

    @GetAPIDocumentation
    @GetMapping("/remittance/{reference}")
    @Operation(summary = "Find Remittance by reference.")
    public ResponseEntity<RemittanceDataDTO> findAndSaveRemittanceData(@PathVariable String reference) {
        Optional<RemittanceDataDTO> remittanceData = remittanceDataService.findAndSaveRemittanceData(reference);
        return remittanceData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetAPIDocumentation
    @GetMapping("/remittance")
    @Operation(summary = "Find Remittance by reference.")
    public ResponseEntity<List<RemittanceDataDTO>> findAllByExchangeCodeAndReferenceNo(@RequestParam String exchangeCode, @RequestParam String reference) {
        List<RemittanceDataDTO> remittanceDataDTOList = remittanceDataService.findAllByExchangeCodeAndReferenceNo(exchangeCode, reference);
        return ResponseEntity.ok().body(remittanceDataDTOList);
    }

    @GetAPIDocumentation
    @GetMapping("/remittance/{exchangeCode}/{sourceType}/{status}")
    @Operation(summary = "Find Remittance by reference.")
    public ResponseEntity<List<RemittanceDataDTO>> findAllByExchangeCodeAndSourceTypeAndProcessStatus(@PathVariable String exchangeCode, @PathVariable String sourceType, @PathVariable String status) {
        List<RemittanceDataDTO> remittanceDataDTOList = remittanceDataService.findAllByExchangeCodeAndSourceTypeAndProcessStatus(exchangeCode, sourceType, status);
        return ResponseEntity.ok().body(remittanceDataDTOList);
    }

    @GetAPIDocumentation
    @GetMapping("/remittance/{exchangeCode}/{reference}/{middlewarePush}/{processStatuses}")
    @Operation(summary = "Find remittance by exchange code, reference number, middleware push flag, and process status.")
    public ResponseEntity<RemittanceDataDTO> getRemittance(@PathVariable String exchangeCode, @PathVariable String reference, @PathVariable int middlewarePush, @PathVariable String processStatus) {
        Optional<RemittanceDataDTO> optionalRemittanceData = remittanceDataService.findByExchangeCodeAndReferenceNoAndMiddlewarePushAndProcessStatus(exchangeCode, reference, middlewarePush, processStatus);
        return optionalRemittanceData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }


}
