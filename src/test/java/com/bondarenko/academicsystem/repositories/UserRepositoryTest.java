package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.LoginDto;
import com.bondarenko.academicsystem.dto.user.UserIdRoleDto;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private final User user = new User();

    @BeforeEach
    void setUp() {
        user.setFullName("Test User");
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    @Test
    void findByUsername() {
        Optional<LoginDto> loginDto = userRepository.findByUsername(user.getUsername());
        assertTrue(loginDto.isPresent());
        assertEquals(user.getUsername(), loginDto.get().getUsername());
        assertEquals(user.getPassword(), loginDto.get().getPassword());
        assertEquals(Role.ADMIN, loginDto.get().getRole());
    }

    @Test
    void deleteByUsername() {
        userRepository.deleteByUsername(user.getUsername());
        Optional<LoginDto> loginDto = userRepository.findByUsername(user.getUsername());
        assertFalse(loginDto.isPresent());
    }

    @Test
    void existsByUsername() {
        assertTrue(userRepository.existsByUsername(user.getUsername()));
        assertFalse(userRepository.existsByUsername("testUser1"));
    }

    @Test
    void findUserIdByUsername() {
        Optional<UserIdRoleDto> userIdRoleDto = userRepository.findUserIdByUsername("testUser");
        assertTrue(userIdRoleDto.isPresent());
        assertEquals(userIdRoleDto.get().getUserId(), user.getId());
        assertEquals(userIdRoleDto.get().getRole(), user.getRole());
    }
}