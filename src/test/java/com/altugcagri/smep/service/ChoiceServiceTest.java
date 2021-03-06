package com.altugcagri.smep.service;

import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.exception.CreatedByException;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ChoiceRepository;
import com.altugcagri.smep.persistence.QuestionRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.service.implementation.ChoiceServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ChoiceServiceTest extends AbstractServiceTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ConfigurableConversionService smepConversionService;

    @InjectMocks
    private final ChoiceService sut = new ChoiceServiceImpl(choiceRepository, questionRepository,
            smepConversionService);

    @Test(expected = ResourceNotFoundException.class)
    public void testCreateChoiceByQuestionId_QuestionNotFound() {
        //Prepare
        final ChoiceRequest request = TestUtils.createDummyChoiceRequest();
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.empty());

        //Test
        sut.createChoiceByQuestionId(currentUser, request);
    }


    @Test(expected = CreatedByException.class)
    public void testCreateChoiceByQuestionId_CreateByFail() {
        //Prepare
        final ChoiceRequest request = TestUtils.createDummyChoiceRequest();
        final Question question = TestUtils.createDummyQuestion();
        question.setCreatedBy(1L);
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.of(question));

        //Test
        sut.createChoiceByQuestionId(currentUser, request);
    }

    @Test
    public void testCreateChoiceByQuestionId_Success() {
        //Prepare
        final ChoiceRequest request = TestUtils.createDummyChoiceRequest();
        final Question question = TestUtils.createDummyQuestion();
        final Choice choice = TestUtils.createDummyChoice();
        question.setCreatedBy(currentUser.getId());
        when(questionRepository.findById(request.getQuestionId())).thenReturn(Optional.of(question));
        when(smepConversionService.convert(request, Choice.class)).thenReturn(choice);

        //Test
        final ResponseEntity<ApiResponse> responseEntity = sut.createChoiceByQuestionId(currentUser, request);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteChoiceById_ChoiceNotFound() {
        //Prepare
        when(choiceRepository.findById(0L)).thenReturn(Optional.empty());

        //Test
        sut.deleteChoiceById(currentUser, 0L);
    }


    @Test(expected = CreatedByException.class)
    public void testDeleteChoiceById_CreateByFail() {
        //Prepare
        final Choice choice = TestUtils.createDummyChoice();
        choice.setCreatedBy(1L);
        when(choiceRepository.findById(0L)).thenReturn(Optional.of(choice));

        //Test
        sut.deleteChoiceById(currentUser, 0L);
    }

    @Test
    public void testDeleteChoiceById_Success() {
        //Prepare
        final Choice choice = TestUtils.createDummyChoice();
        choice.setCreatedBy(currentUser.getId());
        when(choiceRepository.findById(0L)).thenReturn(Optional.of(choice));

        //Test
        final ResponseEntity<ApiResponse> responseEntity = sut.deleteChoiceById(currentUser, 0L);

        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }
}
