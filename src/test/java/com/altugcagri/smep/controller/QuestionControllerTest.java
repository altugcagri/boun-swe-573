package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.QuestionService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController sut = new QuestionController(questionService);

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .answeredChoices(new HashSet<>()).enrolledTopics(new HashSet<>()).build());
    }

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
