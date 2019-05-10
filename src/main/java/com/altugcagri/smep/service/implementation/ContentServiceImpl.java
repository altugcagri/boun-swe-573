package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.ContentRepository;
import com.altugcagri.smep.persistence.TopicRepository;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ContentService;
import com.altugcagri.smep.service.util.SmeptUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    private static final String TOPIC = "Topic";

    private ContentRepository contentRepository;

    private TopicRepository topicRepository;

    private ConfigurableConversionService smepConversionService;

    public ContentServiceImpl(ContentRepository contentRepository, TopicRepository topicRepository,
            ConfigurableConversionService smepConversionService) {
        this.contentRepository = contentRepository;
        this.topicRepository = topicRepository;
        this.smepConversionService = smepConversionService;
    }

    @Override
    public ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser,
            ContentRequest contentRequest) {

        final Topic topic = topicRepository.findById(contentRequest.getTopicId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(TOPIC, "id", contentRequest.getTopicId().toString()));

        SmeptUtilities.checkCreatedBy(TOPIC, currentUser.getId(), topic.getCreatedBy());

        final Content content = smepConversionService.convert(contentRequest, Content.class);
        content.setTopic(topic);
        contentRepository.save(content);
        return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
    }

    @Override
    public ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId) {

        final Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content", "id", contentId.toString()));

        return ResponseEntity.ok().body(smepConversionService.convert(content, ContentResponse.class));
    }


    @Override
    public ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId) {

        final Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content", "id", contentId.toString()));

        SmeptUtilities.checkCreatedBy("Content", currentUser.getId(), content.getCreatedBy());

        contentRepository.delete(content);
        return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
    }

}
