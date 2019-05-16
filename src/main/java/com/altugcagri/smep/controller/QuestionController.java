package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.AnswerRequest;
import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.LearningStepsResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse> createQuestionByContentId(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestionByContentId(currentUser, questionRequest);
    }

    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestionById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long questionId) {
        return questionService.deleteQuestionById(questionId, currentUser);
    }

    @Transactional
    @GetMapping("/{contentId}")
    public ResponseEntity<LearningStepsResponse> getQuestionsByContentId(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return questionService.getLearningSteps(currentUser, contentId);
    }

    @Transactional
    @PostMapping(value = "/answer/")
    public ResponseEntity<ApiResponse> giveAnswer(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody AnswerRequest answerRequest) {
        return questionService.giveAnswer(currentUser, answerRequest);
    }
}