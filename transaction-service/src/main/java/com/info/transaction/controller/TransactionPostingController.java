package com.info.transaction.controller;

import com.info.dto.constants.Constants;
import com.info.transaction.service.StartPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.TRANSACTION)
public class TransactionPostingController {

    private final StartPostingService startPostingService;

    @PostMapping("/post/{reference}")
    public ResponseEntity<String> createPayment(@PathVariable String reference) {
        startPostingService.postPayment(reference);
        return ResponseEntity.ok("Reference " + reference + " is being processed!");
    }
}

