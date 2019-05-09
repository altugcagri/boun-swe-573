package com.altugcagri.smep.security;

import com.altugcagri.smep.persistence.UserRepository;
import com.altugcagri.smep.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        final User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "UserEntity not found with username or email : " + usernameOrEmail));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("UserEntity not found with id : " + id));

        return UserPrincipal.create(user);
    }

}