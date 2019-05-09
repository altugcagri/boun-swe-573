package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ContentRepository;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.QuestionService;
import com.altugcagri.smep.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    private ContentRepository contentRepository;

    private ConfigurableConversionService smepConversionService;

    public QuestionServiceImpl(QuestionRepository questionRepository, ContentRepository contentRepository,
            ConfigurableConversionService smepConversionService) {
        this.questionRepository = questionRepository;
        this.contentRepository = contentRepository;
        this.smepConversionService = smepConversionService;
    }

    @Override
    public ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser,
            QuestionRequest questionRequest) {

        final Content content = contentRepository.findById(questionRequest.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("Content", "id",
                        questionRequest.getContentId().toString()));

        SmeptUtilities.checkCreatedBy("Content", currentUser.getId(), content.getCreatedBy());

        final Question question = smepConversionService.convert(questionRequest, Question.class);
        question.setContent(content);
        questionRepository.save(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question created successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser, Long questionId,
            Choice choiceRequest) {

        final Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId.toString()));

        SmeptUtilities.checkCreatedBy("Question", currentUser.getId(), question.getCreatedBy());

        choiceRequest.setQuestion(question);
        question.getChoiceList().add(choiceRequest);
        questionRepository.save(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser) {

        final Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId.toString()));

        SmeptUtilities.checkCreatedBy("Question", currentUser.getId(), question.getCreatedBy());

        questionRepository.delete(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
    }
}
