package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.exception.authException.NoSuchMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    @DisplayName("회원 추가")
    void add() {
        Member member = Member.createMember("abc", "김태현","1234");
        memberServiceImpl.add(member);

        Member findMember = memberServiceImpl.find(member.getId());

        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);
        assertEquals(member, findMember);
    }

    @Test
    @DisplayName("회원 검색 (단건)")
    void find() {
        Member member = Member.createMember("abc", "김태현","1234");
        memberServiceImpl.add(member);

        Member findMember = memberServiceImpl.find(member.getId());

        assertEquals(member.getUsername(), findMember.getUsername());
        assertEquals(member.getPassword(), findMember.getPassword());
    }


    @Test
    @DisplayName("회원 검색 (N건)")
    void findAll() {
        memberServiceImpl.add(Member.createMember("abc1","김태현", "1234"));
        memberServiceImpl.add(Member.createMember("abc2","김태현", "1234"));
        memberServiceImpl.add(Member.createMember("abc3","김태현", "1234"));
        memberServiceImpl.add(Member.createMember("abc4","김태현", "1234"));
        memberServiceImpl.add(Member.createMember("abc5","김태현", "1234"));

        List<Member> members = memberServiceImpl.findAll();

        assertEquals(5, members.size());
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() {
        Member member = Member.createMember("abc","김태현", "1234");

        memberServiceImpl.add(member);
        assertEquals(member, memberServiceImpl.find(member.getId()));

        memberServiceImpl.delete(member.getId());
        assertThrows(NoSuchMemberException.class, ()-> memberServiceImpl.find(member.getId()));
    }
}