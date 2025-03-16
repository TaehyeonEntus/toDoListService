package com.taehyeon.toDoListService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskSearchCondition {
    private String searchParam;
    private TaskSearchType searchType;
    private TaskStatus status;
}
