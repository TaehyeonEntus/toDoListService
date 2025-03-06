package com.taehyeon.toDoListService.repository;

import com.taehyeon.toDoListService.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
