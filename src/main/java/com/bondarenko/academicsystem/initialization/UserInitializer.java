package com.bondarenko.academicsystem.initialization;

import com.bondarenko.academicsystem.models.Role;
import com.bondarenko.academicsystem.repositories.UserRepository;
import com.bondarenko.academicsystem.enteties.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String[] args) {
        if (userRepository.findByUsername("root").isEmpty()) {
            User defaultUser = new User();
            defaultUser.setFullName("Nazar Bondarenko");
            defaultUser.setUsername("root");
            defaultUser.setPassword(passwordEncoder.encode("1111"));
            defaultUser.setRole(Role.ADMIN);

            userRepository.save(defaultUser);
            log.info("Default user created: root / 1111");
        } else {
            log.info("Default user already exists!");
        }
    }
}
