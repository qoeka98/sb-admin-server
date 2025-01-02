package com.qoeka98.admin.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Configuration
public class JwtConfig {

    Key key;
    // 24시간 유효 토큰 만료 시간 설정 24 * 60 * 60 * 1000
    long tokenValidMilisecond = 1 * 60 * 60 * 1000;

    // application.yml 에서 jwt.secret 값을 가져온다.
    public JwtConfig(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 생성 함수
    public String createToken(Long userId) {
        // 현재 시간 년월일시분초 가져온다.
        Date now = new Date();
        // 만료 시간 계산한다.
        Date validity = new Date(now.getTime() + tokenValidMilisecond);

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // 토큰 생성 함수
    }

    // 토큰에 저장된 데이터 가져오는 함수
    public Claims getTokenClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)  // Key를 SecretKey로 캐스팅
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



}
