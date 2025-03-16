package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@Transactional
class TaskServiceImplTest {
    @Autowired
    TaskService taskService;
    @Autowired
    MemberService memberService;
    @Test
    @DisplayName("할일 추가")
    public void 추가() throws Exception{
        //given
        Member member = Member.createMember("김태현", "흑갑충", "1234");
        Task task = Task.createTask("제목", "내용", LocalDateTime.now().plusWeeks(1));

        //when
        task.assignTo(member);
        Long memberId = memberService.add(member);
        Long taskId = taskService.add(task);

        //then
        Task findTask = taskService.find(taskId);

        Assertions.assertEquals(task,findTask);
        Assertions.assertEquals(member,findTask.getMember());
    }

    @Test
    @DisplayName("할일 수정")
    public void 수정() throws Exception{
        //given
        Member member = memberService.find(1L);
        Task oldTask = Task.createTask("제목", "내용", LocalDateTime.now().plusWeeks(1));
        oldTask.assignTo(member);

        Long memberId = memberService.add(member);
        Long oldTaskId = taskService.add(oldTask);

        //when
        Task newTask = Task.createTask("new 제목", "new 내용", LocalDateTime.now().plusWeeks(2));
        taskService.update(oldTaskId, newTask);
        //then

        Assertions.assertEquals(oldTask.getTitle(), newTask.getTitle());
        Assertions.assertEquals(oldTask.getCaption(), newTask.getCaption());
        Assertions.assertEquals(oldTask.getDueDate(), newTask.getDueDate());
    }

    @Test
    @DisplayName("검색(Page)")
    public void 검색() throws Exception{
        //given
        Member member = memberService.find(1L);
        memberService.add(member);


        PageRequest pageRequest = PageRequest.of(1, 10);
        //when
        Page<Task> page = taskService.findPage(pageRequest, new TaskSearchCondition("", TaskSearchType.TITLE, TaskStatus.TODO));
        //then
        Assertions.assertEquals(10, page.getSize());
        Assertions.assertEquals(99, page.getTotalElements());
    }

}