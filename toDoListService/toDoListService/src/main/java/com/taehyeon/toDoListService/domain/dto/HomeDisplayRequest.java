package com.taehyeon.toDoListService.domain.dto;

import com.taehyeon.toDoListService.domain.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HomeDisplayRequest {
    private List<Task> tasks;

    public HomeDisplayRequest(List<Task> tasks) {
        this.tasks = tasks;
    }
}
