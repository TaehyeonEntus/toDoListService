package com.taehyeon.toDoListService.domain;

import com.taehyeon.toDoListService.domain.dto.MemberRegisterRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String nickname;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Task> tasks = new ArrayList<>();

    //==생성 메서드==//
    public static Member createMember(String username, String nickname, String password) {
        Member member = new Member();
        member.username = username;
        member.nickname = nickname;
        member.password = password;
        return member;
    }

    public Member(MemberRegisterRequest memberRegisterRequest) {
        this.username = memberRegisterRequest.getUsername();
        this.nickname = memberRegisterRequest.getNickname();
        this.password = memberRegisterRequest.getPassword();
    }
}
