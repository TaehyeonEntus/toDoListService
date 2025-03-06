package com.taehyeon.toDoListService.exception.authException;

public class PasswordMismatchException extends AuthException{
    public PasswordMismatchException() {
        super("두 패스워드가 일치하지 않습니다.");
    }
}
