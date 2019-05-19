package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.AnswerRequest;
import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.LearningStepsResponse;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

    ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, QuestionRequest questionRequest);

    ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser);

    ResponseEntity<LearningStepsResponse> getLearningSteps(UserPrincipal currentUser, Long contentId);

    ResponseEntity<ApiResponse> giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest);

}
