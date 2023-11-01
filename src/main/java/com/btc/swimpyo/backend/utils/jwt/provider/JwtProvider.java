package com.btc.swimpyo.backend.utils.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {

    @Value("${accessToken-valid-seconds}")
    private Long accessTokenExpireTimeMs;
    @Value("${refreshToken-valid-seconds}")
    private Long RefreshTokenExpireTimeMs;

    public String createAccessToken(String email, String key, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("email",  email);

        // 권한 정보를 문자열로 변환하여 클레임에 추가
        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authoritiesString);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenExpireTimeMs * 1000)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }
    public String createRefreshToken(String email, String key) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("email",  email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (RefreshTokenExpireTimeMs * 1000)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }






}
