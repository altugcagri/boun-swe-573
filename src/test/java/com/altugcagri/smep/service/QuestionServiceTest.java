package com.altugcagri.smep.service;

import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.exception.CreatedByException;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ContentRepository;
import com.altugcagri.smep.persistence.LearningStepRepository;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.service.implementation.QuestionServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QuestionServiceTest extends AbstractServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private LearningStepRepository learningStepRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;


    @InjectMocks
    private final QuestionService sut = new QuestionServiceImpl(questionRepository, contentRepository,
            smepConversionService, learningStepRepository);


    @Test(expected = ResourceNotFoundException.class)
    public void testCreateQuestionByContentId_ContentNotFound() {
        //Prepare
        final QuestionRequest questionRequest = TestUtils.createDummyQuestionRequest();
        when(contentRepository.findById(questionRequest.getContentId())).thenReturn(Optional.empty());
        //Test
        sut.createQuestionByContentId(currentUser, questionRequest);
    }

    @Test(expected = CreatedByException.class)
    public void testCreateQuestionByContentId_CreateByFail() {
        //Prepare
        final QuestionRequest questionRequest = TestUtils.createDummyQuestionRequest();
        final Content content = TestUtils.createDummyContent();
        content.setCreatedBy(1L);
        when(contentRepository.findById(questionRequest.getContentId())).thenReturn(Optional.of(content));
        //Test
        sut.createQuestionByContentId(currentUser, questionRequest);
    }

    @Test
    public void testCreateQuestionByContentId_Success() {
        //Prepare
        final QuestionRequest questionRequest = TestUtils.createDummyQuestionRequest();
        final Content content = TestUtils.createDummyContent();
        final Question question = TestUtils.createDummyQuestion();
        content.setCreatedBy(currentUser.getId());
        when(contentRepository.findById(questionRequest.getContentId())).thenReturn(Optional.of(content));
        when(smepConversionService.convert(questionRequest, Question.class)).thenReturn(question);
        //Test
        ResponseEntity<ApiResponse> responseEntity = sut.createQuestionByContentId(currentUser, questionRequest);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteQuestionById_ContentNotFound() {
        //Prepare
        when(questionRepository.findById(0L)).thenReturn(Optional.empty());
        //Test
        sut.deleteQuestionById(0L, currentUser);
    }

    @Test(expected = CreatedByException.class)
    public void testDeleteQuestionById_CreateByFail() {
        //Prepare
        final Question question = TestUtils.createDummyQuestion();
        question.setCreatedBy(1L);
        when(questionRepository.findById(0L)).thenReturn(Optional.of(question));
        //Test
        sut.deleteQuestionById(0L, currentUser);
    }

    @Test
    public void testDeleteQuestionById_Success() {
        //Prepare
        //Prepare
        final Question question = TestUtils.createDummyQuestion();
        question.setCreatedBy(currentUser.getId());
        when(questionRepository.findById(0L)).thenReturn(Optional.of(question));
        //Test
        ResponseEntity<ApiResponse> responseEntity = sut.deleteQuestionById(0L, currentUser);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }
}
