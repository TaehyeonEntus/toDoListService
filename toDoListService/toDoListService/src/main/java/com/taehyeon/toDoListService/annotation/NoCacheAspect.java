package com.taehyeon.toDoListService.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NoCacheAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Around("@annotation(NoCache)")
    public Object applyNoCache(ProceedingJoinPoint joinPoint) throws Throwable {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return joinPoint.proceed();
    }
}