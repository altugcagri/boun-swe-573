package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ContentService {

    ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser, ContentRequest contentRequest);

    ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId);

    ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId);

}
