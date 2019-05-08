package com.altugcagri.smep.service.implementation;


import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.TopicRepository;
import com.altugcagri.smep.persistence.UserRepository;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<List<Topic>> getAllTopics(UserPrincipal currentUser) {
        List<Topic> topics = topicRepository.findAll();
        return ResponseEntity.ok().body(topics);
    }

    public ResponseEntity<List<Topic>> getTopicsCreatedBy(String username, UserPrincipal currentUser) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        List<Topic> topicList = topicRepository.findByCreatedBy(user.getId());

        return ResponseEntity.ok().body(topicList);
    }

    public ResponseEntity<Topic> getTopicById(Long topicId, UserPrincipal currentUser) {
        Topic topicById = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("TopicEntity", "id", topicId));

        return ResponseEntity.ok().body(topicById);
    }

    public ResponseEntity<Topic> createTopic(Topic topicRequest) {
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());
        Topic createdTopic = topicRepository.save(topic);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(location).body(createdTopic);
    }

    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())) {
            topicRepository.deleteById(topicId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete topic"));
    }


}
