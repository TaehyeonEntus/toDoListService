package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.exception.authException.NoSuchMemberException;
import com.taehyeon.toDoListService.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public Long add(Member member) {
        return memberRepository.save(member).getId();
    }

    public Member find(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(NoSuchMemberException::new);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void delete(Long id){
        memberRepository.delete(find(id));
    }
}
