package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/choices")
public class ChoiceController {

    private ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody ChoiceRequest choiceRequest) {
        return choiceService.createChoiceByQuestionId(currentUser, choiceRequest);
    }

    @DeleteMapping("/{choiceId}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteChoiceById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long choiceId) {
        return choiceService.deleteChoiceById(currentUser, choiceId);
    }
}