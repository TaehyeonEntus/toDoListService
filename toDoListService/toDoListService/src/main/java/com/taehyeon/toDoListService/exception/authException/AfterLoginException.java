package com.taehyeon.toDoListService.exception.authException;

public class AfterLoginException extends AuthException {
    public AfterLoginException() {
        super("로그인 후 접근 가능합니다");
    }
}
