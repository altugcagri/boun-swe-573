package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.persistence.model.Question;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ContentService {

    ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId);

    ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, Long contentId,
            Question questionRequest);

    ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId);

}
