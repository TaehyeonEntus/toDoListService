package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.domain.Task;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InitService {
    private final MemberService memberService;
    private final TaskService taskService;

    @PostConstruct
    @Transactional
    public void init() {
        Member member = Member.createMember("김태현", "메로나", "12341234");
        memberService.add(member);

        for(int i = 1; i<100; i++){
            Task task = Task.createTask("제목" + i, "내용" + i, LocalDateTime.now().plusWeeks(i));
            task.assignTo(member);
            taskService.add(task);
        }
    }
}
