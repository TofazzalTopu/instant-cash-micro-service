package com.info.accounts.controller;

import com.info.accounts.annotation.APIDocumentation;
import com.info.accounts.service.AccountService;
import com.info.dto.account.AccountDTO;
import com.info.dto.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.ACCOUNTS)
@Tag(name = "Account Controller", description = "APIs for handling Accounts operations")
public class AccountController {

    private final AccountService accountService;

    @APIDocumentation
    @GetMapping("/{id}")
    @Operation(description = "Find accounts info by id.")
    public ResponseEntity<AccountDTO> findById(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @PathVariable Long id) {
        return ResponseEntity.ok().body(accountService.findById(id));
    }

    @PostMapping
    @APIDocumentation
    @Operation(description = "Save accounts info.")
    public ResponseEntity<AccountDTO> save(@RequestHeader(required = false) String userId, @RequestHeader(required = false) String password, @RequestBody @Valid AccountDTO accountDTO) throws URISyntaxException {
        return ResponseEntity.created(new URI("/")).body(accountService.save(accountDTO));
    }


}
