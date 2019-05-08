package com.altugcagri.smep.controller;


import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ContentService;
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

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody ContentRequest contentRequest) {
        return contentService.createContentByTopicId(currentUser, contentRequest);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return contentService.getContentById(currentUser, contentId);
    }

    @DeleteMapping("/{contentId}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return contentService.deleteContentById(currentUser, contentId);
    }

}
