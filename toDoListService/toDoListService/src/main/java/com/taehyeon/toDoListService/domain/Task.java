package com.taehyeon.toDoListService.domain;

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
    private TaskStatus status;

    //==생성 메서드==//
    public static Task createTask(String title, String caption, LocalDateTime dueDate){
        Task task = new Task();
        task.title = title;
        task.caption = caption;
        task.dueDate = dueDate;
        task.status = TaskStatus.TODO;
        return task;
    }

    //==양방향 관계 메서드==//
    public void assignTo(Member member){
        this.member = member;
        member.getTasks().add(this);
    }

    //==수정 메서드==//
    public Task changeTitle(String title){
        this.title = title;
        return this;
    }

    public Task changeCaption(String caption){
        this.caption = caption;
        return this;
    }
    public Task changeDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
        return this;
    }

    public Task changeStatus(TaskStatus status){
        this.status = status;
        return this;
    }

    public Task(TaskDTO taskDTO){
        this.id = taskDTO.getId();
        this.title = taskDTO.getTitle();
        this.caption = taskDTO.getCaption();
        this.member = taskDTO.getMember();
        this.dueDate = taskDTO.getDueDate();
        this.status = taskDTO.getStatus();
    }
}
