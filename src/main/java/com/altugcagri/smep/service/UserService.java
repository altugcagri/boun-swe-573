package com.altugcagri.smep.service;

import com.altugcagri.smep.controller.dto.response.UserIdentityAvailability;
import com.altugcagri.smep.controller.dto.response.UserProfile;
import com.altugcagri.smep.controller.dto.response.UserSummary;
import com.altugcagri.smep.security.UserPrincipal;

public interface UserService {

    UserSummary getCurrentUser(UserPrincipal currentUser);

    UserIdentityAvailability checkUsernameAvailability(String email);

    UserProfile getUserProfile(String username);
}
