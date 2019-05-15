package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ChoiceService;
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
public class ChoiceControllerTest {

    @Mock
    private ChoiceService choiceService;

    @InjectMocks
    private ChoiceController sut = new ChoiceController(choiceService);

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .answeredChoices(new HashSet<>()).enrolledTopics(new HashSet<>()).build());
    }


    @Test
    public void testCreateChoiceByQuestionId() {
        //Prepare
        final ChoiceRequest request = ChoiceRequest.builder().questionId(0L).text("someText").correct(true).build();
        //Test
        sut.createChoiceByQuestionId(currentUser, request);
        //Verify
        verify(choiceService, times(1)).createChoiceByQuestionId(currentUser, request);
    }


    @Test
    public void testDeleteChoiceById() {
        //Test
        sut.deleteChoiceById(currentUser, 0L);
        //Verify
        verify(choiceService, times(1)).deleteChoiceById(currentUser, 0L);
    }

}
