package com.taehyeon.toDoListService.domain;

import com.taehyeon.toDoListService.domain.dto.TaskAddRequest;
import com.taehyeon.toDoListService.domain.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String caption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    //==생성 메서드==//
    public static Task createTask(String title, String caption, LocalDateTime dueDate){
        Task task = new Task();
        task.title = title;
        task.caption = caption;
        task.dueDate = dueDate;
        task.taskStatus = TaskStatus.TODO;
        return task;
    }

    //==양방향 관계 메서드==//
    public void assignTo(Member member){
        this.member = member;
        member.getTasks().add(this);
    }

    //==수정 메서드==//
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeCaption(String caption){
        this.caption = caption;
    }
    public void changeDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
    }

    public void changeStatus(TaskStatus status){
        this.taskStatus = status;
    }

    public Task(TaskDTO taskDTO){
        this.id = taskDTO.getId();
        this.title = taskDTO.getTitle();
        this.caption = taskDTO.getCaption();
        this.member = taskDTO.getMember();
        this.dueDate = taskDTO.getDueDate();
        this.taskStatus = taskDTO.getStatus();
    }

    public Task(TaskAddRequest taskAddRequest){
        this.title = taskAddRequest.getTitle();
        this.caption = taskAddRequest.getCaption();
        this.dueDate = taskAddRequest.getDueDate();
    }
}
