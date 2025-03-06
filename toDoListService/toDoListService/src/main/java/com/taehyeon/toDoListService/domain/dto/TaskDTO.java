package com.taehyeon.toDoListService.domain.dto;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.domain.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String caption;
    private Member member;
    private LocalDateTime dueDate;
    private TaskStatus status;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.caption = task.getCaption();
        this.member = task.getMember();
        this.dueDate = task.getDueDate();
        this.status = task.getTaskStatus();
    }
}
