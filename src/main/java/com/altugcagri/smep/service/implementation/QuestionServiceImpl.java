package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser, Long questionId,
            Choice choiceRequest) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())) {
            choiceRequest.setQuestion(question);
            question.getChoiceList().add(choiceRequest);
            questionRepository.save(question);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create choice"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())) {
            questionRepository.deleteQuestionById(questionId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete question"));
    }
}
