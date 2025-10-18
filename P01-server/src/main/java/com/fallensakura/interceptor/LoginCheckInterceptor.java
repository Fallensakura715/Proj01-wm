package com.fallensakura.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    public boolean preHandle() {
        return false;
    }
}
