package com.altugcagri.smep.controller;


import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ContentService;
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

@Slf4j
@RestController
@RequestMapping(value = "/api/contents")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody ContentRequest contentRequest) {
        return contentService.createContentByTopicId(currentUser, contentRequest);
    }

    @Transactional
    @GetMapping(value = "/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return contentService.getContentById(currentUser, contentId);
    }

    @Transactional
    @DeleteMapping(value = "/{contentId}")
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return contentService.deleteContentById(currentUser, contentId);
    }

}
