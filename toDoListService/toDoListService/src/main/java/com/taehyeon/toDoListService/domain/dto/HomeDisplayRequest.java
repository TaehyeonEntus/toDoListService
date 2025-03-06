package com.taehyeon.toDoListService.domain.dto;

import com.taehyeon.toDoListService.domain.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HomeDisplayRequest {
    private String nickname;
    private List<Task> tasks;

    public HomeDisplayRequest(String nickname, List<Task> tasks) {
        this.nickname = nickname;
        this.tasks = tasks;
    }
}
