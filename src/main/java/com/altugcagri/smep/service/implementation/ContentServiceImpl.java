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
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

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
                .orElseThrow(() -> new ResourceNotFoundException("TopicEntity", "id", contentRequest.getTopicId()));

        if (currentUser.getId().equals(topic.getCreatedBy())) {

            Content content = smepConversionService.convert(contentRequest, Content.class);
            content.setTopic(topic);
            contentRepository.save(content);

            return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create content"));
    }

    @Override
    public ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId) {

        Content contentById = contentRepository.findById(contentId).orElse(null);
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setId(contentById.getId());
        contentResponse.setTitle(contentById.getTitle());
        contentResponse.setText(contentById.getText());
        contentResponse.setTopicId(contentById.getTopic().getId());

        if (contentById != null) {
            return ResponseEntity.ok().body(contentResponse);
        }
        return ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId) {
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null && currentUser.getId().equals(content.getCreatedBy())) {
            contentRepository.deleteContentById(contentId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Content not found"));
    }

}
