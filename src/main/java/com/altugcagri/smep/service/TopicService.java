package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.EnrollmentRequest;
import com.altugcagri.smep.controller.dto.request.PublishRequest;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {

    ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser);

    ResponseEntity<List<TopicResponse>> getTopicsCreatedBy(String username, UserPrincipal currentUser);

    ResponseEntity<TopicResponse> getTopicById(Long topicId, UserPrincipal currentUser);

    ResponseEntity<TopicResponse> createTopic(UserPrincipal currentUser, TopicRequest topicRequest);

    ResponseEntity<ApiResponse> publishStatusUpdate(UserPrincipal currentUser, PublishRequest publishRequest);

    ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser, EnrollmentRequest enrollmentRequest);

    ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId);
}
