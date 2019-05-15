package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.LoginRequest;
import com.altugcagri.smep.controller.dto.request.SignUpRequest;
import com.altugcagri.smep.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private AuthService authenticate;

    public AuthController(AuthService authenticate) {
        this.authenticate = authenticate;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticate.authenticateUser(loginRequest);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticate.registerUser(signUpRequest);
    }
}
