package com.info.api.controller;

import com.info.api.annotation.GetAPIDocumentation;
import com.info.api.annotation.PostApiDocumentation;
import com.info.dto.constants.Constants;
import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICPaymentStatusDTO;
import com.info.api.service.impl.common.ApiService;
import com.info.api.service.ic.ICUnlockRemittanceService;
import com.info.api.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.INSTANT_CASH_WRITE)
@Tag(name = "Instant Cash", description = "APIs for handling Instant Cash remittance operations")
public class InstantCashController {

    private final ApiService apiService;
    private final ICUnlockRemittanceService icUnlockRemittanceService;

    @GetAPIDocumentation
    @GetMapping(value = "/paymentStatus")
    @Operation(summary = "Check payment status by exchange code and reference PIN.")
    public ResponseEntity<APIResponse<ICPaymentStatusDTO>> getPaymentStatus(@RequestHeader @NotBlank String userId, @RequestHeader @NotBlank String password,
                                                                            @RequestParam String exchcode, @RequestParam @NotBlank String reference) {
        return ResponseEntity.ok().body(apiService.getPaymentStatus(exchcode, reference));
    }

    @PostApiDocumentation
    @PostMapping(value = "/unlock")
    @Operation(summary = "Unlock remittance by reference PIN.")
    public ResponseEntity<APIResponse<String>> unlockRemittance(@RequestHeader String userId, @RequestHeader String password, @RequestParam String reference) {
        return ResponseEntity.ok().body(icUnlockRemittanceService.unlockICOutstandingRemittance(reference, ApiUtil.getICExchangeProperties()));
    }

}

