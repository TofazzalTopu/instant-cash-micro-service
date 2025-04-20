package com.info.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Welcome Controller", description = "Welcome Controller")
public class WelcomeController {


    @GetMapping
    @Operation(description = "Welcome Message.")
    public String welcome(){
        return "App is running successfully!";
    }
}
