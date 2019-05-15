package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.response.UserIdentityAvailability;
import com.altugcagri.smep.controller.dto.response.UserProfile;
import com.altugcagri.smep.controller.dto.response.UserSummary;
import com.altugcagri.smep.exception.ResourceNotFoundException;
import com.altugcagri.smep.persistence.TopicRepository;
import com.altugcagri.smep.persistence.UserRepository;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping(value = "/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping(value = "/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "email") String email) {
        return new UserIdentityAvailability(!userRepository.existsByEmail(email));
    }

    @GetMapping(value = "/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        final long topicCount = topicRepository.countByCreatedBy(user.getId());

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(),
                topicCount);
    }

}
