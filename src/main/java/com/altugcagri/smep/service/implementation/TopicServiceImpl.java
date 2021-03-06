package com.altugcagri.smep.service.implementation;


import com.altugcagri.smep.controller.dto.request.EnrollmentRequest;
import com.altugcagri.smep.controller.dto.request.PublishRequest;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.exception.NotValidTopicException;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.TopicRepository;
import com.altugcagri.smep.persistence.UserRepository;
import com.altugcagri.smep.persistence.WikiDataRepository;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.persistence.model.WikiData;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.TopicService;
import com.altugcagri.smep.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    private static final String TOPIC = "Topic";

    private TopicRepository topicRepository;

    private UserRepository userRepository;

    private WikiDataRepository wikiDataRepository;

    private ConfigurableConversionService smepConversionService;

    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository,
            WikiDataRepository wikiDataRepository, ConfigurableConversionService smepConversionService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.smepConversionService = smepConversionService;
        this.wikiDataRepository = wikiDataRepository;
    }

    @Override
    public ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser) {
        return ResponseEntity.ok().body(topicRepository.findByPublished(true).stream()
                .map(topic -> smepConversionService.convert(topic, TopicResponse.class)).collect(
                        Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<TopicResponse>> getTopicsCreatedBy(String username, UserPrincipal currentUser) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return ResponseEntity.ok().body(topicRepository.findByCreatedBy(user.getId()).stream()
                .map(topic -> smepConversionService.convert(topic, TopicResponse.class)).collect(
                        Collectors.toList()));
    }

    @Override
    public ResponseEntity<TopicResponse> getTopicById(Long topicId, UserPrincipal currentUser) {
        final Topic topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        return ResponseEntity.ok().body(smepConversionService.convert(topic, TopicResponse.class));
    }

    @Override
    public ResponseEntity<TopicResponse> createTopic(UserPrincipal currentUser, TopicRequest topicRequest) {

        final List<WikiData> nonExistWikiDataSet =
                topicRequest.getWikiData() != null ? topicRequest.getWikiData().stream()
                        .filter(wikiData -> !wikiDataRepository.existsById(wikiData.getId()))
                        .collect(Collectors.toList()) : null;

        wikiDataRepository.saveAll(nonExistWikiDataSet);

        topicRepository.findById(topicRequest.getId())
                .ifPresent(topic -> topicRequest.setEnrolledUsers(topic.getEnrolledUsers()));

        topicRequest.setCreatedByName(currentUser.getUsername());

        final Topic topic = topicRepository.save(smepConversionService.convert(topicRequest, Topic.class));

        return ResponseEntity.ok().body(smepConversionService.convert(topic, TopicResponse.class));
    }

    @Override
    public ResponseEntity<ApiResponse> publishStatusUpdate(UserPrincipal currentUser, PublishRequest publishRequest) {
        final Topic topic = topicRepository.findById(publishRequest.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", publishRequest.getTopicId().toString()));

        SmeptUtilities.checkCreatedBy(TOPIC, currentUser.getId(), topic.getCreatedBy());

        if (publishRequest.isPublish()) {

            if(topic.getContentList() == null || topic.getContentList().isEmpty()){
                throw new NotValidTopicException(topic.getTitle(),
                        "All topics must have at least one content. Please Check Your Topic!");
            }

            topic.getContentList().forEach(content -> {
                if (content.getQuestionList() == null || content.getQuestionList().isEmpty()) {
                    throw new NotValidTopicException(topic.getTitle(),
                            "All contents must have at least one question. Please Check Your Contents!");
                }
            });
        }

        topic.setPublished(publishRequest.isPublish());
        topicRepository.save(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Topic published successfully"));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser) {
        final Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        SmeptUtilities.checkCreatedBy(TOPIC, currentUser.getId(), topic.getCreatedBy());

        topicRepository.delete(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
    }

    @Override
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser,
            EnrollmentRequest enrollmentRequest) {
        final Topic topic = topicRepository.findById(enrollmentRequest.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "topicId",
                        enrollmentRequest.getTopicId().toString()));
        final User user = userRepository.findByUsername(enrollmentRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", enrollmentRequest.getUsername()));
        topic.getEnrolledUsers().add(user);
        topicRepository.save(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrolled to topic successfully"));

    }

    @Override
    public ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        final List<Topic> enrolledTopics = topicRepository.findTopicByEnrolledUsersContainsAndPublished(user, true);

        return ResponseEntity.ok()
                .body(enrolledTopics.stream().map(topic -> smepConversionService.convert(topic, TopicResponse.class))
                        .collect(Collectors.toList()));
    }
}
