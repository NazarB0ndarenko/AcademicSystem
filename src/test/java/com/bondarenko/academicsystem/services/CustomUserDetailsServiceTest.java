package com.bondarenko.academicsystem.services;

import static org.junit.jupiter.api.Assertions.*;

import com.bondarenko.academicsystem.dto.LoginDto;
import com.bondarenko.academicsystem.dto.user.UserIdRoleDto;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.UserRepository;
import com.bondarenko.academicsystem.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists() {
        String username = "testUser";
        LoginDto mockLoginDto = new LoginDto(username, "encodedPassword", Role.ADMIN);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockLoginDto));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void createUser_Success() {
        User mockUser = new User();
        mockUser.setFullName("Test User");
        mockUser.setPassword("rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        customUserDetailsService.createUser(mockUser);

        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(mockUser);
        assertEquals("encodedPassword", mockUser.getPassword());
    }

    @Test
    void deleteUser_Success() {
        String username = "testUser";

        customUserDetailsService.deleteUser(username);

        verify(userRepository, times(1)).deleteByUsername(username);
    }

    @Test
    void userExists_UserExists() {
        String username = "existingUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean result = customUserDetailsService.userExists(username);

        assertTrue(result);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void userExists_UserDoesNotExist() {
        String username = "nonExistentUser";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        boolean result = customUserDetailsService.userExists(username);

        assertFalse(result);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void findUserRole_UserExists() {
        String username = "testUser";
        UserIdRoleDto mockDto = new UserIdRoleDto(1L, Role.ADMIN);
        when(userRepository.findUserIdByUsername(username)).thenReturn(Optional.of(mockDto));

        Optional<UserIdRoleDto> result = customUserDetailsService.findUserRole(username);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getUserId());
        assertEquals(Role.ADMIN, result.get().getRole());
        verify(userRepository, times(1)).findUserIdByUsername(username);
    }

    @Test
    void findUserRole_UserNotFound() {
        String username = "nonExistentUser";
        when(userRepository.findUserIdByUsername(username)).thenReturn(Optional.empty());

        Optional<UserIdRoleDto> result = customUserDetailsService.findUserRole(username);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findUserIdByUsername(username);
    }
}
