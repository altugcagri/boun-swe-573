package com.altugcagri.smep.controller;

import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private final UserController sut = new UserController(userService);

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .answeredChoices(new HashSet<>()).enrolledTopics(new HashSet<>()).build());
    }


    @Test
    public void testGetCurrentUser() {
        //Test
        sut.getCurrentUser(currentUser);
        //Verify
        verify(userService, times(1)).getCurrentUser(currentUser);
    }

    @Test
    public void testCheckUsernameAvailability() {
        //Test
        sut.checkUsernameAvailability("email");
        //Verify
        verify(userService, times(1)).checkUsernameAvailability("email");
    }

    @Test
    public void getGetUserProfile() {
        //Test
        sut.getUserProfile("username");
        //Verify
        verify(userService, times(1)).getUserProfile("username");
    }

}
