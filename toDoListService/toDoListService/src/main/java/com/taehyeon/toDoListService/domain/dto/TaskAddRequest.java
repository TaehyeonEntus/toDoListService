package com.taehyeon.toDoListService.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskAddRequest {
    @NotEmpty
    private String title;

    private String caption;

    @Future
    private LocalDateTime dueDate;

    public TaskAddRequest(String title, String caption, LocalDateTime dueDate) {
        this.title = title;
        this.caption = caption;
        this.dueDate = dueDate;
    }
}
