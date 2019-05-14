package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

    ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, QuestionRequest questionRequest);

    ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser, Long questionId,
            Choice choiceRequest);

    ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser);

}