package com.info.auth.controller;

import com.info.auth.annotation.GetAPIDocumentation;
import com.info.auth.annotation.PostApiDocumentation;
import com.info.auth.dto.JwtRequest;
import com.info.auth.dto.JwtResponse;
import com.info.auth.dto.RefreshTokenRequest;
import com.info.auth.model.User;
import com.info.auth.security.JwtTokenUtil;
import com.info.auth.service.JwtUserDetailsServiceImpl;
import com.info.auth.service.UserService;
import com.info.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@CrossOrigin
@RestController
@RequestMapping
@Tag(name = "Authentication APIs", description = "APIs for handling Auth operations")
public class AuthenticationController {
    public static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, JwtTokenUtil jwtTokenUtil, JwtUserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @PostApiDocumentation
    @PostMapping(value = "/authenticate")
    @Operation(summary = "Authenticate login user.")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String accessToken = jwtTokenUtil.generateToken(userDetails, true);
        final String refresh_token = jwtTokenUtil.generateToken(userDetails, false);

        return ResponseEntity.ok(new JwtResponse(accessToken, refresh_token));
    }

    @PostApiDocumentation
    @PostMapping(value = "/register")
    @Operation(summary = "Register user.")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO user) {
        logger.info("user: {}", user);
        return ResponseEntity.ok(userService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetAPIDocumentation
    @PostMapping("/refresh")
    @Operation(summary = "Generate token using refresh token.")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
        if (!jwtTokenUtil.isTokenValid(request.getRefreshToken()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");

        String username = jwtTokenUtil.getUsernameFromToken(request.getRefreshToken());
        String newAccessToken = jwtTokenUtil.doGenerateToken(new HashMap<>(), username, false);
        return ResponseEntity.ok(new JwtResponse(newAccessToken, request.getRefreshToken()));
    }

}