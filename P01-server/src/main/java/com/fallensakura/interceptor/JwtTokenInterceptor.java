package com.fallensakura.interceptor;

import com.fallensakura.constant.JwtClaimsConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.properties.JwtProperties;
import com.fallensakura.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    JwtProperties jwtProperties;

//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) {
//
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//
//        String token = request.getHeader(jwtProperties.getAdminTokenName());
//
//        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//
//        token = token.substring(7);
//
//        try {
//            log.info("Parsing JWT Token: {}", token);
//            Long id = JwtUtil.praseToken(token, jwtProperties.getAdminSecretKey())
//                            .get(JwtClaimsConstant.EMPLOYEE_ID, Long.class);
//
//            log.info("Token parse success: {}", id);
//            BaseContext.setCurrentId(id);
//            return true;
//        } catch (Exception e) {
//            log.error("Token parse failed: {}", e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//    }
}
