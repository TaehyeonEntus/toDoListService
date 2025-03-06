package com.taehyeon.toDoListService.exception.authException;

public class NoSuchMemberException extends AuthException {
    public NoSuchMemberException() {
        super("회원을 찾을 수 없습니다.");
    }
}
