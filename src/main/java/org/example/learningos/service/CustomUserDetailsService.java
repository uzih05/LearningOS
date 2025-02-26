package org.example.learningos.service;

import java.util.Optional;
import org.example.learningos.model.User;
import org.example.learningos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(user);
    }
}
