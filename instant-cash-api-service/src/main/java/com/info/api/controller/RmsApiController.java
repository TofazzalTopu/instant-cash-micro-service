package com.info.api.controller;

import com.info.api.annotation.APIDocumentation;
import com.info.api.constants.Constants;
import com.info.api.dto.PaymentApiResponse;
import com.info.api.dto.SearchApiRequest;
import com.info.api.dto.SearchApiResponse;
import com.info.api.dto.ic.APIResponse;
import com.info.api.dto.ic.ICTransactionReportDTO;
import com.info.api.dto.ic.TransactionReportRequestBody;
import com.info.api.service.impl.common.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_ENDPOINT)
@Tag(name = "RMS API", description = "APIs for handling remittance operations")
public class RmsApiController {

    private final ApiService apiService;

    @APIDocumentation
    @GetMapping(value = "/remittance")
    @Operation(description = "Searches remittance data by reference PIN and exchange code.")
    public ResponseEntity<SearchApiResponse> searchRemittance(@RequestHeader @NotBlank String userId, @RequestHeader String password, @RequestParam String bruserid, @RequestParam String brcode, @RequestParam String exchcode, @RequestParam String pinno, HttpServletRequest request) {
        SearchApiRequest searchApiRequest = new SearchApiRequest(bruserid, brcode, exchcode, pinno, null);
        return ResponseEntity.ok(apiService.searchRemittance(searchApiRequest, request));
    }

    @APIDocumentation
    @PutMapping(value = "/remittance")
    @Operation(description = "Marks a remittance as paid using the payload provided.")
    public ResponseEntity<PaymentApiResponse> payRemittance(@RequestHeader String userId, @RequestHeader String password, @RequestBody String data, HttpServletRequest request) {
        return ResponseEntity.ok(apiService.payRemittance(data, request));
    }

    @APIDocumentation
    @GetMapping(value = "/transaction-report")
    @Operation(description = "Generates transaction reports filtered by exchange code and date range.")
    public ResponseEntity<APIResponse<List<ICTransactionReportDTO>>> transactionReport(@RequestHeader @NotBlank String userId, @RequestHeader @NotBlank String password,
                                                                                       @RequestParam @NotBlank String exchcode, @RequestParam @NotBlank String fromDate,
                                                                                       @RequestParam @NotBlank String toDate, @RequestParam @Min(1) int pageNumber, @RequestParam @Min(1) int pageSize, HttpServletRequest request) {
        TransactionReportRequestBody report = new TransactionReportRequestBody(userId, password, null, null, exchcode, fromDate, toDate, pageNumber, pageSize);
        return ResponseEntity.ok(apiService.fetchTransactionReport(report));
    }


}

