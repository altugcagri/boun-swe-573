package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.EnrollmentRequest;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TopicControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController sut = new TopicController(topicService);

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .answeredChoices(new HashSet<>()).enrolledTopics(new HashSet<>()).build());
    }


    @Test
    public void testGetAllTopics() {
        //Test
        sut.getAllTopics(currentUser);
        //Verify
        verify(topicService, times(1)).getAllTopics(currentUser);
    }


    @Test
    public void testGetTopicsByUsername() {
        //Test
        sut.getTopicsByUsername("someName", currentUser);
        //Verify
        verify(topicService, times(1)).getTopicsCreatedBy("someName", currentUser);
    }

    @Test
    public void testGetTopicsById() {
        //Test
        sut.getTopicById(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).getTopicById(0L, currentUser);
    }

    @Test
    public void testCreateTopic() {
        //Prepare
        final TopicRequest request = TopicRequest.builder().contentList(new ArrayList<>()).description("description")
                .id(0L).imageUrl("someUrl").title("title").wikiData(new HashSet<>()).build();
        //Test
        sut.createTopic(request);
        //Verify
        verify(topicService, times(1)).createTopic(request);
    }

    @Test
    public void testDeleteTopicById() {
        //Test
        sut.deleteTopicById(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).deleteTopicById(0L, currentUser);
    }

    @Test
    public void testEnrollToTopicByUsername() {
        //Prepare
        final EnrollmentRequest request = EnrollmentRequest.builder().topicId(0L).username("username").build();
        //Test
        sut.enrollToTopicByUsername(currentUser, request);
        //Verify
        verify(topicService, times(1)).enrollToTopicByUsername(currentUser, request);
    }

    @Test
    public void testGetEnrolledTopics() {
        //Test
        sut.getEnrolledTopics(currentUser, 0L);
        //Verify
        verify(topicService, times(1)).getTopicsByEnrolledUserId(currentUser, 0L);
    }
}
