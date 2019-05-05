package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Topic>> getTopicsByUsername(@PathVariable String username,
            @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<Topic> getTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody Topic topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @PostMapping("/{topicId}/contents")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId, @Valid @RequestBody Content contentRequest) {
        return topicService.createContentByTopicId(currentUser, topicId, contentRequest);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }
}
