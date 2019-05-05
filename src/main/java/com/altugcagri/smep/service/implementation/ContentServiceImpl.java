package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.persistence.ContentRepository;
import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    private ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

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

    public ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, Long contentId,
            Question questionRequest) {
        Content contentById = contentRepository.findById(contentId).orElse(null);

        if (contentById != null && currentUser.getId().equals(contentById.getCreatedBy())) {
            questionRequest.setContent(contentById);
            contentById.getQuestionList().add(questionRequest);
            contentRepository.save(contentById);

            return ResponseEntity.ok().body(new ApiResponse(true, "Question created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create question"));
    }

    public ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId) {
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null && currentUser.getId().equals(content.getCreatedBy())) {
            contentRepository.deleteContentById(contentId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Content not found"));
    }

}
