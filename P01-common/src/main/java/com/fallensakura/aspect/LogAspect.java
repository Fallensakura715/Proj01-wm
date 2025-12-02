package com.fallensakura.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.fallensakura..service..*.*(..))")
    public void servicePointCut() {}

    @Pointcut("execution(* com.fallensakura..controller..*(..))")
    public void controllerPointCut() {}

    @Around("servicePointCut()")
    public Object serviceLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Service called: {}.{}, args: {}", className, methodName, args);

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("Service error: {}.{}, error: {}", className, methodName, e.getMessage(), e);
            throw e;
        }
    }

    @Around("controllerPointCut()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String method = request.getMethod();
        String path = request.getRequestURI();
        String params = request.getQueryString();
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("[{}] {} completed in {}ms, result: {}", method, path, duration, result);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("[{}] {} error in {}ms, error: {}", method, path, duration, e.getMessage(), e);
            throw e;
        }
    }
}
