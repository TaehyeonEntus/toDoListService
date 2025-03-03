package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Member;

import java.util.List;

public interface MemberService {
    Long add(Member member);
    Member find(Long id);
    Member findByUsername(String username);
    List<Member> findAll();
    void delete(Long id);
}
