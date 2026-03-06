package com.fallensakura.interceptor;

import com.fallensakura.constant.JwtClaimsConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.properties.JwtProperties;
import com.fallensakura.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private static final String USER_LOGIN_TOKEN_PREFIX = "user:login:token:";

    private final JwtProperties jwtProperties;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());

        if (!StringUtils.hasText(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            log.info("Parsing User JWT Token...");
            Long userId = JwtUtils.parseToken(token, jwtProperties.getUserSecretKey())
                    .get(JwtClaimsConstant.USER_ID, Long.class);

            Object cacheToken = redisTemplate.opsForValue().get(USER_LOGIN_TOKEN_PREFIX + userId);
            if (cacheToken == null || !token.equals(cacheToken.toString())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception e) {
            log.error("User token parse failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.clear();
    }
}
