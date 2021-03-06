package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ChoiceService {

    ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser,
            ChoiceRequest choiceRequest);

    ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId);
}
