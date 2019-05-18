package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.LoginRequest;
import com.altugcagri.smep.controller.dto.request.SignUpRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse> registerUser(SignUpRequest signUpRequest);

    ResponseEntity authenticateUser(LoginRequest loginRequest);

}
