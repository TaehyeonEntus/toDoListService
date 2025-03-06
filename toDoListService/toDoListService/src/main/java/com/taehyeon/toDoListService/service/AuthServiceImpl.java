package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.domain.dto.MemberLoginRequest;
import com.taehyeon.toDoListService.domain.dto.MemberRegisterRequest;
import com.taehyeon.toDoListService.exception.authException.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;

    public Long login(MemberLoginRequest memberLoginRequest, HttpSession session) {
        Member member = memberService.findByUsername(memberLoginRequest.getUsername());
        session.setAttribute("username", member.getUsername());
        return member.getId();
    }

    public Long join(MemberRegisterRequest memberRegisterRequest) {
        return memberService.add(new Member(memberRegisterRequest));
    }

    public void authenticateUser(MemberLoginRequest memberLoginRequest) throws AuthException {
        Member member = memberService.findByUsername(memberLoginRequest.getUsername());
        String password = memberLoginRequest.getPassword();
        try {
            validatePasswordsMatch(member.getPassword(), password);
        } catch (PasswordMismatchException e) {
            throw new InvalidPasswordException();
        }
    }

    public void validateUsername(String username) throws AuthException {
        try {
            memberService.findByUsername(username);
            throw new DuplicateUsernameException();
        }catch (NoSuchMemberException e) {
            //정상 동작
        }
    }

    public void validatePasswordsMatch(String pw1, String pw2){
        if(!pw1.equals(pw2)){
            throw new PasswordMismatchException();
        }
    }
}
