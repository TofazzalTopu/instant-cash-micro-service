package com.info.auth.controller;

import com.info.auth.annotation.GetAPIDocumentation;
import com.info.auth.annotation.PostApiDocumentation;
import com.info.auth.dto.Credential;
import com.info.dto.constants.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(Constants.LOGIN)
@Tag(name = "Login APIs", description = "APIs for handling Auth operations")
public class AuthController {

    @Value("${jwt.secret}")
    private String secret;

    @GetMapping
    @GetAPIDocumentation
    @Operation(summary = "Generate token.")
    public String getToken() {
        System.out.println("inside auth login");
        return Jwts.builder()
                .claim("id", "tofazzal")
                .claim("role", "admin")
                .setSubject("Test Token")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    @PostMapping
    @PostApiDocumentation
    public String getTokenProvidingUsernameAndPassword(@RequestBody Credential credential) {
        System.out.println("inside getTokenProvidingUsernameAndPassword");
        return Jwts.builder()
                .claim("id", credential.getUserName())
                .claim("role", credential.getRole())
                .setSubject("Test Token")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

}