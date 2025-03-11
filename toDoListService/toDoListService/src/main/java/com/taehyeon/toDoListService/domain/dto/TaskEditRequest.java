package com.taehyeon.toDoListService.domain.dto;

import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.domain.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskEditRequest {
    private Long id;
    @NotEmpty
    private String title;

    @NotEmpty
    private String caption;

    @Future(message = "현재 시각 이후의 날짜를 입력 해라")
    private LocalDateTime dueDate;
    private TaskStatus status;

    public TaskEditRequest(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.caption = task.getCaption();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
    }
}
