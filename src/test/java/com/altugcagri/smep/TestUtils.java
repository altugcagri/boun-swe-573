package com.altugcagri.smep;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.controller.dto.request.EnrollmentRequest;
import com.altugcagri.smep.controller.dto.request.LoginRequest;
import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.controller.dto.request.SignUpRequest;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.persistence.model.WikiData;
import com.altugcagri.smep.security.UserPrincipal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {

    public static ChoiceRequest createDummyChoiceRequest() {
        final ChoiceRequest choiceRequest = new ChoiceRequest();
        choiceRequest.setCorrect(true);
        choiceRequest.setQuestionId(0L);
        choiceRequest.setText("someText");
        return choiceRequest;
    }

    public static Choice createDummyChoice() {
        final Choice choice = new Choice();
        choice.setCorrect(true);
        choice.setId(0L);
        choice.setText("someText");
        return choice;
    }

    public static ContentRequest createDummyContentRequest() {
        final ContentRequest contentRequest = new ContentRequest();
        contentRequest.setId(0L);
        contentRequest.setTitle("someTitle");
        contentRequest.setTopicId(0L);
        contentRequest.setText("someText");
        return contentRequest;
    }

    public static Content createDummyContent() {
        final Content content = new Content();
        content.setId(0L);
        content.setTitle("someTitle");
        content.setText("someText");
        content.setTopic(createDummyTopic());
        return content;
    }

    public static ContentResponse createDummyContentResponse() {
        final ContentResponse contentResponse = new ContentResponse();
        contentResponse.setId(0L);
        contentResponse.setTitle("someTitle");
        contentResponse.setText("someText");
        contentResponse.setTopicId(0L);
        return contentResponse;
    }

    public static Topic createDummyTopic() {
        final Topic topic = new Topic();
        topic.setId(0L);
        topic.setDescription("someDescription");
        topic.setTitle("someTitle");
        topic.setImageUrl("someImgUrl");
        topic.setWikiDataSet(createDummyWikiDataSet());
        topic.setEnrolledUsers(createDummyUserSet());
        return topic;
    }

    public static List<Topic> createDummyTopicList() {
        final List<Topic> topicList = new ArrayList<>();
        topicList.add(createDummyTopic());
        return topicList;
    }

    public static Question createDummyQuestion() {
        final Question question = new Question();
        question.setId(0L);
        question.setText("someText");
        question.setContent(createDummyContent());
        return question;
    }

    public static QuestionRequest createDummyQuestionRequest() {
        final QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setContentId(0L);
        questionRequest.setText("someText");
        return questionRequest;
    }

    public static TopicRequest createDummyTopicRequest() {
        final TopicRequest topicRequest = new TopicRequest();
        topicRequest.setEnrolledUsers(createDummyUserSet());
        topicRequest.setWikiData(createDummyWikiDataSet());
        topicRequest.setContentList(createDummyContentList());
        topicRequest.setId(0L);
        topicRequest.setDescription("someDescription");
        topicRequest.setImageUrl("someImageUrl");
        topicRequest.setTitle("someTitle");
        return topicRequest;
    }

    public static TopicResponse createDummyTopicResponse() {
        final TopicResponse topicResponse = new TopicResponse();
        topicResponse.setWikiData(createDummyWikiDataSet());
        topicResponse.setContentList(createDummyContentList());
        topicResponse.setId(0L);
        topicResponse.setDescription("someDescription");
        topicResponse.setImageUrl("someImageUrl");
        topicResponse.setTitle("someTitle");
        return topicResponse;
    }

    public static WikiData createDummyWikiData() {
        final WikiData wikiData = new WikiData();
        wikiData.setId("id");
        wikiData.setDescription("someDescription");
        wikiData.setConceptUri("someConceptUri");
        wikiData.setLabel("someLabel");
        return wikiData;
    }

    public static Set<WikiData> createDummyWikiDataSet() {
        final Set<WikiData> wikiDataSet = new HashSet<>();
        wikiDataSet.add(createDummyWikiData());
        return wikiDataSet;
    }

    public static List<Content> createDummyContentList() {
        final List<Content> contentList = new ArrayList<>();
        contentList.add(createDummyContent());
        return contentList;
    }

    public static User createDummyUser() {
        final User user = new User();
        user.setEmail("email");
        user.setId(0L);
        user.setName("name");
        user.setPassword("pass");
        user.setUsername("userName");
        return user;
    }

    public static Set<User> createDummyUserSet() {
        final Set<User> userSet = new HashSet<>();
        userSet.add(createDummyUser());
        return userSet;
    }

    public static UserPrincipal createDummyCurrentUser() {
        return UserPrincipal
                .create(User.builder().name("name").username("username").email("email").id(0L).password("pass")
                        .enrolledTopics(new HashSet<>()).build());
    }

    public static EnrollmentRequest createDummyEnrollmentRequest() {
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
        enrollmentRequest.setTopicId(0L);
        enrollmentRequest.setUsername("username");
        return enrollmentRequest;
    }

    public static SignUpRequest createDummySignUpRequest() {
        final SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("email");
        signUpRequest.setName("name");
        signUpRequest.setPassword("pass");
        signUpRequest.setUsername("username");
        return signUpRequest;

    }

    public static LoginRequest createDummyLoginRequest() {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsernameOrEmail("usernameOrEmail");
        return loginRequest;
    }
}
