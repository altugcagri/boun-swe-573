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
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("Question Not Found", "id", choiceRequest.getQuestionId()));

        if (currentUser.getId().equals(question.getCreatedBy())) {
            final Choice choice = smepConversionService.convert(choiceRequest, Choice.class);
            choice.setQuestion(question);
            choiceRepository.save(choice);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create choice"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {
        Choice choice = choiceRepository.findById(choiceId).orElse(null);
        if (choice != null && currentUser.getId().equals(choice.getCreatedBy())) {
            choiceRepository.deleteById(choiceId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete choice"));
    }
}
