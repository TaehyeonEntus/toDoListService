package com.taehyeon.toDoListService.exception.authException;

public class BeforeLoginException extends AuthException{
    public BeforeLoginException() {
        super("이미 로그인 된 사용자입니다.");
    }
}
