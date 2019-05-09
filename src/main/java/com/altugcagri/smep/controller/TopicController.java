package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<TopicResponse>> getTopicsByUsername(@PathVariable String username,
            @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }
}
