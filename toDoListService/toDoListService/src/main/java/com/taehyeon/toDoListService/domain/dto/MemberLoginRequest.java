package com.taehyeon.toDoListService.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginRequest {
    @NotEmpty(message = "아이디 입력 해라")
    private String username;

    @NotEmpty(message = "비밀번호 입력 해라")
    private String password;

    public MemberLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
