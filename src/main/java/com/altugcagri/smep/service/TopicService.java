package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {

    ResponseEntity<List<Topic>> getAllTopics(UserPrincipal currentUser);

    ResponseEntity<List<Topic>> getTopicsCreatedBy(String username, UserPrincipal currentUser);

    ResponseEntity<Topic> getTopicById(Long topicId, UserPrincipal currentUser);

    ResponseEntity<Topic> createTopic(Topic topicRequest);


    ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser);
}
