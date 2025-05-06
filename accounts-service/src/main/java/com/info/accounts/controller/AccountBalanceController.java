package com.info.accounts.controller;

import com.info.accounts.annotation.APIDocumentation;
import com.info.accounts.service.AccountBalanceService;
import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.dto.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.ACCOUNTS)
@Tag(name = "Account Balance Controller", description = "APIs for handling Account Balance operations")
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;


    @APIDocumentation
    @PostMapping("/balance")
    @Operation(description = "Save account balance info.")
    public ResponseEntity<AccountBalanceDTO> save(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        return ResponseEntity.created(new URI(Constants.ACCOUNTS)).body(accountBalanceService.save(paymentDTO));
    }


    @APIDocumentation
    @GetMapping("/balance/{id}")
    @Operation(description = "Find account balance by id.")
    public ResponseEntity<AccountBalanceDTO> findById(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @PathVariable @NotNull Long id) {
        return ResponseEntity.ok().body(accountBalanceService.findById(id));
    }

    @APIDocumentation
    @GetMapping("/balance/number/{accountNumber}")
    @Operation(description = "Find accounts info by account number.")
    public ResponseEntity<AccountBalanceDTO> save(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @PathVariable @NotNull String accountNumber) {
        return ResponseEntity.ok().body(accountBalanceService.findByAccountNumber(accountNumber));
    }


}
