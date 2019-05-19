package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.request.AnswerRequest;
import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.LearningStepsResponse;
import com.altugcagri.smep.controller.dto.response.QuestionResponse;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ContentRepository;
import com.altugcagri.smep.persistence.LearningStepRepository;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.LearningStep;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.QuestionService;
import com.altugcagri.smep.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final String CONTENT = "Content";
    private static final String QUESTION = "Question";

    private QuestionRepository questionRepository;

    private ContentRepository contentRepository;

    private ConfigurableConversionService smepConversionService;

    private LearningStepRepository learningStepRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ContentRepository contentRepository,
            ConfigurableConversionService smepConversionService, LearningStepRepository learningStepRepository) {
        this.questionRepository = questionRepository;
        this.contentRepository = contentRepository;
        this.smepConversionService = smepConversionService;
        this.learningStepRepository = learningStepRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser,
            QuestionRequest questionRequest) {

        final Content content = contentRepository.findById(questionRequest.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException(CONTENT, "id",
                        questionRequest.getContentId().toString()));

        SmeptUtilities.checkCreatedBy(CONTENT, currentUser.getId(), content.getCreatedBy());

        final Question question = smepConversionService.convert(questionRequest, Question.class);
        question.setContent(content);
        questionRepository.save(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question created successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser) {

        final Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION, "id", questionId.toString()));

        SmeptUtilities.checkCreatedBy(QUESTION, currentUser.getId(), question.getCreatedBy());

        questionRepository.delete(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
    }

    @Override
    public ResponseEntity<LearningStepsResponse> getLearningSteps(UserPrincipal currentUser, Long contentId) {

        final List<QuestionResponse> questionResponseList = new ArrayList<>();
        final AtomicReference<String> contentTitle = new AtomicReference<>();
        final AtomicReference<String> topicTitle = new AtomicReference<>();
        final AtomicReference<Long> topicId = new AtomicReference<>();
        final AtomicReference<Long> nextContentId = new AtomicReference<>();

        contentRepository.findById(contentId)
                .ifPresent(content -> {
                    content.getTopic().getContentList().stream()
                            .map(Content::getId).collect(Collectors.toList()).stream().filter(id -> id > contentId).min(
                            Comparator.comparing(Long::valueOf)).ifPresent(nextContentId::set);
                    final List<LearningStep> learningSteps = learningStepRepository
                            .findByUserIdAndContentId(currentUser.getId(), contentId);
                    final List<Question> questionList = content.getQuestionList();
                    if (questionList != null) {
                        for (Question question : questionList) {
                            final QuestionResponse resp = new QuestionResponse();
                            final List<Choice> choiceList = question.getChoiceList();
                            resp.setChoiceList(choiceList);
                            resp.setId(question.getId());
                            resp.setText(question.getText());
                            learningSteps.stream()
                                    .filter(learningStep -> learningStep.getQuestionId().equals(question.getId()))
                                    .findAny()
                                    .ifPresent(step -> choiceList.stream()
                                            .filter(choice -> choice.getId().equals(step.getAnswerId())).findFirst()
                                            .ifPresent(
                                                    resp::setUserAnswer));
                            questionResponseList.add(resp);
                        }
                    }
                    contentTitle.set(content.getTitle());
                    topicTitle.set(content.getTopic().getTitle());
                    topicId.set(content.getTopic().getId());
                });

        return ResponseEntity.ok()
                .body(LearningStepsResponse.builder().questions(questionResponseList).contentId(contentId)
                        .topicId(topicId.get()).contentTitle(contentTitle.get())
                        .topicTitle(topicTitle.get()).nextContentId(nextContentId.get()).build());
    }

    @Override
    public ResponseEntity<ApiResponse> giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest) {

        final Question question = questionRepository.findById(answerRequest.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION, "id", answerRequest.getQuestionId().toString()));

        final Content content = question.getContent();

        final LearningStep learningStep = learningStepRepository
                .findByUserIdAndContentIdAndQuestionIdAndAnswerId(currentUser.getId(), content.getId(),
                        answerRequest.getQuestionId(),
                        answerRequest.getChoiceId()).orElse(learningStepRepository
                        .save(LearningStep.builder().userId(currentUser.getId())
                                .answerId(answerRequest.getChoiceId())
                                .questionId(answerRequest.getQuestionId()).contentId(content.getId()).build()));

        return ResponseEntity.ok().body(new ApiResponse(true,
                "For User " + learningStep.getUserId() + " LearningStep is created successfully"));
    }
}
