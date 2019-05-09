package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {

    ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser);

    ResponseEntity<List<TopicResponse>> getTopicsCreatedBy(String username, UserPrincipal currentUser);

    ResponseEntity<TopicResponse> getTopicById(Long topicId, UserPrincipal currentUser);

    ResponseEntity<TopicResponse> createTopic(TopicRequest topicRequest);

    ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser);
}
