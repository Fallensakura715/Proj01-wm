package com.fallensakura.interceptor;

import com.fallensakura.constant.JwtClaimsConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.properties.JwtProperties;
import com.fallensakura.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());

        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        token = token.substring(7);

        try {
            log.info("Parsing User JWT Token...");
            Long userId = JwtUtils.parseToken(token, jwtProperties.getUserSecretKey())
                    .get(JwtClaimsConstant.USER_ID, Long.class);
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
