package com.altugcagri.smep.controller;

import com.altugcagri.smep.controller.dto.response.UserIdentityAvailability;
import com.altugcagri.smep.controller.dto.response.UserProfile;
import com.altugcagri.smep.controller.dto.response.UserSummary;
import com.altugcagri.smep.security.CurrentUser;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @GetMapping(value = "/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }

    @Transactional
    @GetMapping(value = "/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "email") String email) {
        return userService.checkUsernameAvailability(email);
    }

    @Transactional
    @GetMapping(value = "/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {

        return userService.getUserProfile(username);
    }

}
