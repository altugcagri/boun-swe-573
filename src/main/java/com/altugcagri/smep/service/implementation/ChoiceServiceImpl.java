package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ChoiceRepository;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ChoiceService;
import com.altugcagri.smep.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChoiceServiceImpl implements ChoiceService {

    private ChoiceRepository choiceRepository;

    private QuestionRepository questionRepository;

    private ConfigurableConversionService smepConversionService;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository, QuestionRepository questionRepository,
            ConfigurableConversionService smepConversionService) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.smepConversionService = smepConversionService;
    }

    @Override
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser,
            ChoiceRequest choiceRequest) {

        final Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("Question", "id", choiceRequest.getQuestionId().toString()));

        SmeptUtilities.checkCreatedBy("Question", currentUser.getId(), question.getCreatedBy());

        final Choice choice = smepConversionService.convert(choiceRequest, Choice.class);
        choice.setQuestion(question);
        choiceRepository.save(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {

        final Choice choice = choiceRepository.findById(choiceId).orElseThrow(
                () -> new ResourceNotFoundException("Choice", "id", choiceId.toString()));

        SmeptUtilities.checkCreatedBy("Choice", currentUser.getId(), choice.getCreatedBy());

        choiceRepository.delete(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
    }
}
