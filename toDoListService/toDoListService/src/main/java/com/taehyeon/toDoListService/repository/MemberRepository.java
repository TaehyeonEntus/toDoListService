package com.taehyeon.toDoListService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taehyeon.toDoListService.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
