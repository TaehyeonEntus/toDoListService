package com.taehyeon.toDoListService.service;

import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.exception.taskException.NoSuchTaskException;
import com.taehyeon.toDoListService.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Long add(Task task) {
        return taskRepository.save(task).getId();
    }

    @Override
    public Task find(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        taskRepository.delete(find(id));
    }
}
