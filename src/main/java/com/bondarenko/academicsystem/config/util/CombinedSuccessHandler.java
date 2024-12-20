package com.bondarenko.academicsystem.config.util;

import com.bondarenko.academicsystem.models.SecurityUser;
import com.bondarenko.academicsystem.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@AllArgsConstructor
public class CombinedSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);

        // Set the JWT in an HTTP-Only cookie
        Cookie jwtCookie = new Cookie("jwt-token", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Use HTTPS in production
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) jwtService.getExpirationTime() / 1000);
        response.addCookie(jwtCookie);

        // Role-based redirect logic
        String redirectUrl = "/home"; // Default fallback redirect URL
        for (var authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            if ("ROLE_ADMIN".equals(role)) {
                redirectUrl = "/admin/dashboard";
                break;
            } else if ("ROLE_LECTURER".equals(role)) {
                redirectUrl = "/lecturer/home";
                break;
            } else if ("ROLE_STUDENT".equals(role)) {
                redirectUrl = "/student/home";
                break;
            }
        }

        response.sendRedirect(redirectUrl); // Redirect the user based on role
    }

}

