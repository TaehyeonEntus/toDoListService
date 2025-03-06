package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Task;

import java.util.List;

public interface TaskService {
    Long add(Task task);
    Task find(Long id);
    List<Task> findAll();
    void delete(Long id);
}
