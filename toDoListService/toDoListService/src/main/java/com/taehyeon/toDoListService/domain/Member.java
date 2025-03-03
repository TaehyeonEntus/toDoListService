package com.taehyeon.toDoListService.domain;

import com.taehyeon.toDoListService.domain.dto.MemberLoginRequest;
import com.taehyeon.toDoListService.domain.dto.MemberRegisterRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    //==생성 메서드==//
    public static Member create(String username, String password) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        return member;
    }

    public Member(MemberRegisterRequest memberRegisterRequest) {
        this.username = memberRegisterRequest.getUsername();
        this.password = memberRegisterRequest.getPassword();
    }

    public Member(MemberLoginRequest memberLoginRequest) {
        this.username = memberLoginRequest.getUsername();
        this.password = memberLoginRequest.getPassword();
    }
}
