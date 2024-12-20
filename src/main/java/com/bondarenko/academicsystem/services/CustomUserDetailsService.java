package com.bondarenko.academicsystem;

import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.SecurityUser;
import com.bondarenko.academicsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Searching for user with username: {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username: {} doesn't exist ", username);
            return new UsernameNotFoundException(username);
        });

        log.info("User found: {}", user);

        return new SecurityUser(user);
    }

    @Transactional
    public void createUser(User user) {
        log.info("Creating admin: {}", user);

        userRepository.save(user);

        log.info("Admin with user name created: {}", user);
    }


}
