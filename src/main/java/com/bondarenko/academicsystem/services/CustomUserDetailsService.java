package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.LoginDto;
import com.bondarenko.academicsystem.dto.user.UserIdRoleDto;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.SecurityUser;
import com.bondarenko.academicsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Searching for user with username: {}", username);

        LoginDto loginDto = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User with username: {} doesn't exist ", username);
            return new UsernameNotFoundException(username);
        });

        log.info("User found with username: {} found", loginDto.getUsername());

        return new SecurityUser(loginDto);
    }

    public void createUser(User user) {
        log.info("Encoding password for user with name: {}", user.getFullName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Saving user with name: {}", user.getFullName());

        userRepository.save(user);
        log.info("Successfully created user with name: {}", user.getFullName());

    }


    public void deleteUser(String username) {
        log.info("Deleting user with username: {}", username);
        userRepository.deleteByUsername(username);
        log.info("Successfully deleted user with username: {}", username);
    }


    public boolean userExists(String username) {
        log.info("Checking if user with username: {} exists", username);
        return userRepository.existsByUsername(username);
    }

    public Optional<UserIdRoleDto> findUserRole(String username){
        log.info("Finding user role for username: {}", username);
        return userRepository.findUserIdByUsername(username);
    }

}
