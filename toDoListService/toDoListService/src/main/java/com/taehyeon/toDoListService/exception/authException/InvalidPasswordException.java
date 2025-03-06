package com.taehyeon.toDoListService.exception.authException;

public class InvalidPasswordException extends AuthException{
    public InvalidPasswordException() {
        super("유효하지 않은 비밀번호 입니다.");
    }
}
