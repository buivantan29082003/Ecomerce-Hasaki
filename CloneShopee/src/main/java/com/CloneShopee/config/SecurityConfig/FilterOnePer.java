package com.CloneShopee.config.SecurityConfig;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterOnePer extends OncePerRequestFilter {
    private AuthenticationManager authManager;

    public FilterOnePer(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        SecurityContext context = SecurityContextHolder.getContext();
        // System.out.println(token);
        Authentication auth = new AuthenticationCustome(token);
        auth = authManager.authenticate(auth);
        context.setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

}