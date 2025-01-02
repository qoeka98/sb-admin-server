package com.qoeka98.admin.filter;


import com.qoeka98.admin.config.JwtConfig;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtConfig jwtConfig;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // signup, login 요청은 토큰 검증을 하지 않는다.
        if(request.getRequestURI().equals("/api/v1/users/signup") ||
                request.getRequestURI().equals("/api/v1/users/login")||
        request.getRequestURI().startsWith("/api/v1/admin/login")||
         request.getRequestURI().startsWith("/api/v1/restaurants/{id}")||
        request.getRequestURI().startsWith("/api/v1/admin/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 토큰을 가져온다.
        String bearerToken = request.getHeader("Authorization");
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0MSIsImlhdCI6MTczNTI2NTE3NiwiZXhwIjoxNzM1MzUxNTc2fQ.yoquDfBOHhR5YqJwOTbJ4WSUjtpvPRlRcSjNhFTXNAQ

        if (bearerToken == null || bearerToken.isEmpty() ||
                !bearerToken.startsWith("Bearer ")) {
            response.setStatus(401);
            return;
        }

        // 토큰에서 Bearer 제거
        String token = bearerToken.substring(7);
        // 토큰 유효시간 검증
        Claims claims = jwtConfig.getTokenClaims(token);
        Date expiration = claims.getExpiration();
        if(expiration != null && expiration.before(new Date())) {
            response.setStatus(401);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(claims.getSubject(),
                        null, null);
        // SecurityContextHolder 에 인증 정보를 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);


    }
}
