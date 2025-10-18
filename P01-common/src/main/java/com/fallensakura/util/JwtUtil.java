package com.fallensakura.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "this_is_fallensakura's_secret_key";
    private static final long EXPIREATON_TIME = 2 * 60 * 60 * 1000;

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据员工ID生成JwtToken
     * @param employeeId
     * @return JwtToken
     */
    public static String generateToken(Long employeeId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("employeeId", employeeId);

        return Jwts.builder()
                .claims(claims)
                .subject("employee_auth")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIREATON_TIME))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 验证JwtToken
     * @param key
     * @param token
     * @return
     */
    public static Claims praseToken(Key key, String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}