package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.domain.TaskSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService{
    Long update(Long taskId, Task newTask);
    Long add(Task task);
    Task find(Long id);
    Page<Task> findPage(Pageable pageable, TaskSearchCondition condition);
    void delete(Long id);
}
