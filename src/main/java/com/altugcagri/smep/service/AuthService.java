package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.LoginRequest;
import com.altugcagri.smep.controller.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity registerUser(SignUpRequest signUpRequest);

    ResponseEntity authenticateUser(LoginRequest loginRequest);

}
