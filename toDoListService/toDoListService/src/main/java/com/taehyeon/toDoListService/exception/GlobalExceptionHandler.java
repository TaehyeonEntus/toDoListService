package com.taehyeon.toDoListService.exception;

import com.taehyeon.toDoListService.exception.authException.AfterLoginException;
import com.taehyeon.toDoListService.exception.authException.BeforeLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BeforeLoginException.class)
    public ModelAndView handleBeforeLoginException(BeforeLoginException e) {
        ModelAndView mav = new ModelAndView("error");
        System.out.println("e.getMessage() = " + e.getMessage());
        mav.addObject("message", e.getMessage());
        return mav;
    }

    @ExceptionHandler(AfterLoginException.class)
    public ModelAndView handleAfterLoginException(AfterLoginException e) {
        ModelAndView mav = new ModelAndView("error");
        System.out.println("e.getMessage() = " + e.getMessage());
        mav.addObject("message", e.getMessage());
        return mav;
    }
}
