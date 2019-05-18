package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.EnrollmentRequest;
import com.altugcagri.smep.controller.dto.request.PublishRequest;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @Transactional
    @GetMapping(value = "/{username}")
    public ResponseEntity<List<TopicResponse>> getTopicsByUsername(@PathVariable String username,
            @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @Transactional
    @GetMapping(value = "/topic/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping(value = "/publish")
    public ResponseEntity<ApiResponse> publishStatusUpdate(@CurrentUser UserPrincipal currentUser,
            @RequestBody PublishRequest publishRequest) {
        return topicService.publishStatusUpdate(currentUser, publishRequest);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @Transactional
    @DeleteMapping(value = "/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(@CurrentUser UserPrincipal currentUser,
            @RequestBody EnrollmentRequest enrollmentRequest) {
        return topicService.enrollToTopicByUsername(currentUser, enrollmentRequest);
    }

    @Transactional
    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<TopicResponse>> getEnrolledTopics(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long userId) {
        return topicService.getTopicsByEnrolledUserId(currentUser, userId);
    }
}
