package com.taehyeon.toDoListService.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterRequest {
    @NotEmpty(message = "아이디 입력 해라")
    private String username;

    @NotEmpty(message = "닉네임 입력 해라")
    private String nickname;

    @Size(message = "비밀번호는 6자리+", min = 6)
    private String password;

    @Size(message = "비밀번호는 6자리+", min = 6)
    private String passwordConfirm;

    public MemberRegisterRequest(String username, String nickname, String password, String passwordConfirm) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
