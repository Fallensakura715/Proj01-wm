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
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private static final String ADMIN_LOGIN_TOKEN_PREFIX = "admin:login:token:";

    private final JwtProperties jwtProperties;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        token = token.substring(7);

        try {
            log.info("Parsing JWT Token...");
            Long id = JwtUtils.parseToken(token, jwtProperties.getAdminSecretKey())
                            .get(JwtClaimsConstant.EMPLOYEE_ID, Long.class);

            Object cacheToken = redisTemplate.opsForValue().get(ADMIN_LOGIN_TOKEN_PREFIX + id);
            if (cacheToken == null || !token.equals(cacheToken.toString())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            BaseContext.setCurrentId(id);
            return true;
        } catch (Exception e) {
            log.error("Token parse failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.clear();
    }
}
