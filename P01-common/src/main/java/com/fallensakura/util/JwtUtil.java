package com.fallensakura.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static SecretKey getSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据员工ID生成JwtToken
     * @param employeeId
     * @return JwtToken
     */
    public static String generateToken(Long employeeId, String secretKey, Long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("employeeId", employeeId);

        return Jwts.builder()
                .claims(claims)
                .subject("employee_auth")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSecretKey(secretKey), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 根据JWT令牌获取员工ID
     * @param token
     * @return
     */
    public static Long getEmployeeId(String token, String secretKey) {
        return praseToken(token, secretKey).get("employeeId", Long.class);
    }

    /**
     * 验证JwtToken
     * @param token
     * @return
     */
    public static Claims praseToken(String token, String secretKey) {
        return Jwts.parser()
                .verifyWith(getSecretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}