package com.taehyeon.toDoListService.domain.dto;

import com.taehyeon.toDoListService.domain.Task;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class HomeDisplayRequest {
    private List<TaskDTO> tasks;
    private int currentPage;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;

    public HomeDisplayRequest(Page<Task> page) {
        this.tasks = page.getContent().stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList()); // 엔티티 -> DTO 변환
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
    }
}
