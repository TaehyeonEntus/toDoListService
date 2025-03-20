package com.taehyeon.toDoListService.annotation;

import com.taehyeon.toDoListService.exception.authException.BeforeLoginException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class BeforeLoginAspect {
    @Before("@within(com.taehyeon.toDoListService.annotation.BeforeLogin)")
    public void beforeLogin(JoinPoint joinPoint) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpSession session = attributes.getRequest().getSession();

        if(session.getAttribute("memberId") != null)
            throw new BeforeLoginException();
    }
}
