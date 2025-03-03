package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.dto.MemberLoginRequest;
import com.taehyeon.toDoListService.domain.dto.MemberRegisterRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    Long login(MemberLoginRequest memberLoginRequest, HttpSession session);
    Long join(MemberRegisterRequest memberRegisterRequest);
}
