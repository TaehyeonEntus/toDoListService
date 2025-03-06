package com.taehyeon.toDoListService.exception.authException;

public class DuplicateUsernameException extends AuthException{
    public DuplicateUsernameException() {
        super("이미 사용중인 아이디입니다.");
    }
}
