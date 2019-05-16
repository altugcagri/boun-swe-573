package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.service.QuestionService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QuestionControllerTest extends AbstractEntityControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private final QuestionController sut = new QuestionController(questionService);

    @Test
    public void testCreateQuestionByContentId() {
        //Prepare
        final QuestionRequest request = QuestionRequest.builder().contentId(0L).text("someText").build();
        //Test
        sut.createQuestionByContentId(currentUser, request);
        //Verify
        verify(questionService, times(1)).createQuestionByContentId(currentUser, request);
    }


    @Test
    public void testDeleteQuestionById() {
        //Test
        sut.deleteQuestionById(currentUser, 0L);
        //Verify
        verify(questionService, times(1)).deleteQuestionById(0L, currentUser);
    }

}
