package com.info.transaction.controller;

import com.info.dto.constants.Constants;
import com.info.transaction.service.StartPostingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.TRANSACTION)
@Tag(name = "TransactionPostingController", description = "Transaction Posting Operations")
public class TransactionPostingController {

    private final StartPostingService startPostingService;

    @PostMapping("/post/{reference}")
    @Operation(summary = "Transaction posting.")
    public ResponseEntity<String> createPayment(@PathVariable String reference) throws URISyntaxException {
        startPostingService.postPayment(reference);
        return ResponseEntity.created(new URI(Constants.TRANSACTION)).body("Transaction posted successfully for the reference: " + reference);
    }
}

