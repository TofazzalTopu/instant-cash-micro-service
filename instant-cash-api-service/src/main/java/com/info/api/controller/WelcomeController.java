package com.info.api.controller;

import com.info.api.annotation.APIDocumentation;
import com.info.dto.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * WelcomeController is a REST controller that provides a welcome message.
 * It is used to check if the application is running successfully.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.API_ENDPOINT + Constants.INSTANT_CASH)
@Tag(name = "Welcome Controller", description = "Welcome Controller")
public class WelcomeController {
    private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);


    @APIDocumentation
    @GetMapping(value = "/welcome")
    @Operation(description = "Welcome Message.")
    public ResponseEntity<String> welcome() {
        logger.info("Welcome to the Instant Cash API!");
        return ResponseEntity.ok().body("App is running successfully!");
    }
}
