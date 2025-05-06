package com.info.accounts.controller;

import com.info.accounts.annotation.GetAPIDocumentation;
import com.info.accounts.annotation.PostApiDocumentation;
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


    @PostApiDocumentation
    @PostMapping("/balance")
    @Operation(summary = "Save account balance info.")
    public ResponseEntity<AccountBalanceDTO> save(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        return ResponseEntity.created(new URI(Constants.ACCOUNTS)).body(accountBalanceService.save(paymentDTO));
    }

    @PostApiDocumentation
    @PutMapping("/balance")
    @Operation(summary = "Revert account balance.")
    public ResponseEntity<AccountBalanceDTO> revert(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        return ResponseEntity.created(new URI(Constants.ACCOUNTS)).body(accountBalanceService.revertAmount(paymentDTO));
    }

    @GetAPIDocumentation
    @GetMapping("/balance/{id}")
    @Operation(summary = "Find account balance by id.")
    public ResponseEntity<AccountBalanceDTO> findById(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @PathVariable @NotNull Long id) {
        return ResponseEntity.ok().body(accountBalanceService.findById(id));
    }

    @GetAPIDocumentation
    @GetMapping("/balance/number/{accountNumber}")
    @Operation(summary = "Find accounts info by account number.")
    public ResponseEntity<AccountBalanceDTO> save(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @PathVariable @NotNull String accountNumber) {
        return ResponseEntity.ok().body(accountBalanceService.findByAccountNumber(accountNumber));
    }


}
