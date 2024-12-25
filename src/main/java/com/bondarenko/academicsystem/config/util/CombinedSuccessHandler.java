package com.bondarenko.academicsystem.config.util;

import com.bondarenko.academicsystem.models.SecurityUser;
import com.bondarenko.academicsystem.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class CombinedSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        log.info("User {} successfully authenticated", user.getUsername());

        // Generate JWT and add as a cookie
        String jwtToken = jwtService.generateToken(user);
        Cookie jwtCookie = new Cookie("jwt-token", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) jwtService.getExpirationTime() / 1000);
        response.addCookie(jwtCookie);

        // Determine redirect URL based on role
        String redirectUrl = authentication.getAuthorities().stream()
                .map(authority -> switch (authority.getAuthority()) {
                    case "ROLE_ADMIN" -> {
                        log.info("User {} logged in as admin", user.getUsername());
                        yield "/admin/dashboard";
                    }
                    case "ROLE_LECTURER" -> {
                        log.info("User {} logged in as lecturer", user.getUsername());
                        yield "/lecturer/home";
                    }
                    case "ROLE_STUDENT" -> {
                        log.info("User {} logged in as student", user.getUsername());
                        yield "/student/home";
                    }
                    default -> null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (redirectUrl == null) {
            log.error("No valid roles found for user {}", user.getUsername());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No roles assigned to the user.");
            return;
        }

        log.info("Redirecting user {} to {}", user.getUsername(), redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
