package com.taehyeon.toDoListService.controller;

import com.taehyeon.toDoListService.domain.dto.MemberLoginRequest;
import com.taehyeon.toDoListService.domain.dto.MemberRegisterRequest;
import com.taehyeon.toDoListService.exception.authException.DuplicateUsernameException;
import com.taehyeon.toDoListService.exception.authException.InvalidPasswordException;
import com.taehyeon.toDoListService.exception.authException.NoSuchMemberException;
import com.taehyeon.toDoListService.exception.authException.PasswordMismatchException;
import com.taehyeon.toDoListService.service.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("memberLoginRequest", new MemberLoginRequest());

        return "index";
    }

    @GetMapping("register")
    public String registerPage(Model model) {
        model.addAttribute("memberRegisterRequest", new MemberRegisterRequest());

        return "register";
    }

    /**
     * validation
     * 1. DTO가 유효한가?
     * 2. ID가 DB에 존재 하는가?
     * 3. PW가 ID와 매칭 되는가?
     */
    @PostMapping("login")
    public String login(@Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult, HttpSession session) {
        //Dto 유효성 검사
        if (bindingResult.hasErrors()) {
            return "index";
        }

        //회원 유효성 검사
        try {
            authService.authenticateUser(memberLoginRequest);
        } catch (NoSuchMemberException e) {
            bindingResult.rejectValue("username", "username.duplicate", "유효하지 않은 아이디입니다.");
            return "index";
        } catch (InvalidPasswordException e) {
            bindingResult.rejectValue("password", "password.invalid","유효하지 않은 비밀번호 입니다.");
            return "index";
        }
        authService.login(memberLoginRequest, session);

        return "redirect:/home";
    }

    /**
     * validation
     * 1. DTO가 유효한가?
     * 2. ID가 이미 사용 중인가?
     * 3. PW가 PW 확인과 같은가?
     */
    @PostMapping("register")
    public String register(@Valid MemberRegisterRequest memberRegisterRequest, BindingResult bindingResult) {
        //Dto 유효성 검사
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            return "register";
        }

        //아이디 중복 여부 검사
        try {
            authService.validateUsername(memberRegisterRequest.getUsername());
        } catch (DuplicateUsernameException e) {
            bindingResult.rejectValue("username", "username.duplicate", "이미 사용 중인 아이디입니다.");
            return "register";
        }

        //비밀번호 동일 여부 검사
        try{
            authService.validatePasswordsMatch(memberRegisterRequest.getPassword(), memberRegisterRequest.getPasswordConfirm());
        } catch (PasswordMismatchException e) {
            bindingResult.rejectValue("passwordConfirm", "password.mismatch", "비밀번호 확인이 일치하지 않습니다.");
            return "register";
        }

        authService.join(memberRegisterRequest);

        return "redirect:/";
    }
}
