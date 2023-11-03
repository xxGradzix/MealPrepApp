package com.xxgradzix.MealPrepApp.security.auth.controller;

import com.xxgradzix.MealPrepApp.security.auth.entity.AuthenticationRequest;
import com.xxgradzix.MealPrepApp.security.auth.entity.AuthenticationResponse;
import com.xxgradzix.MealPrepApp.security.auth.service.AuthenticationService;
import com.xxgradzix.MealPrepApp.security.auth.entity.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



}
