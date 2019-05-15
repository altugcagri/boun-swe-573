package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ContentService;
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
public class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private ContentController sut = new ContentController(contentService);

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .answeredChoices(new HashSet<>()).enrolledTopics(new HashSet<>()).build());
    }

    @Test
    public void testCreateContentByTopicId() {
        //Prepare
        final ContentRequest request = ContentRequest.builder().id(0L).text("someText").title("title").topicId(0L)
                .build();
        //Test
        sut.createContentByTopicId(currentUser, request);
        //Verify
        verify(contentService, times(1)).createContentByTopicId(currentUser, request);
    }

    @Test
    public void testGetContentById() {
        //Test
        sut.getContentById(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).getContentById(currentUser, 0L);
    }

    @Test
    public void testDeleteContentById() {
        //Test
        sut.deleteContentById(currentUser, 0L);
        //Verify
        verify(contentService, times(1)).deleteContentById(currentUser, 0L);
    }
}
