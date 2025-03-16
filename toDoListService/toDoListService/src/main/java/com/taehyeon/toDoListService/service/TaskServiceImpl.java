package com.taehyeon.toDoListService.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taehyeon.toDoListService.domain.*;
import com.taehyeon.toDoListService.exception.taskException.NoSuchTaskException;
import com.taehyeon.toDoListService.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Long update(Long oldTaskId, Task newTask) {
        Task oldTask = taskRepository.findById(oldTaskId).orElseThrow(NoSuchTaskException::new);

        oldTask.changeCaption(newTask.getCaption())
                .changeTitle(newTask.getTitle())
                .changeDueDate(newTask.getDueDate())
                .changeStatus(newTask.getStatus());

        return oldTaskId;
    }

    @Transactional
    public Long updateStatus(Long taskId, TaskStatus taskStatus) {
        Task task = taskRepository.findById(taskId).orElseThrow(NoSuchTaskException::new);
        task.changeStatus(taskStatus);
        return taskId;
    }

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
    public Page<Task> findPage(Pageable pageable, TaskSearchCondition condition) {
        QTask task = QTask.task;
        QMember member = QMember.member;
        List<Task> list = jpaQueryFactory
                .selectFrom(task)
                .join(task.member, member)
                .where(searchByString(condition.getSearchParam(), condition.getSearchType()), searchByStatus(condition.getStatus()))
                .orderBy(task.dueDate.asc(), task.status.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(task.count())
                .from(task)
                .join(task.member, member)
                .where(searchByString(condition.getSearchParam(), condition.getSearchType()), searchByStatus(condition.getStatus()))
                .fetchOne();

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public void delete(Long id) {
        taskRepository.delete(find(id));
    }

    private BooleanExpression searchByString(String param, TaskSearchType type) {
        QTask task = QTask.task;
        if (param == null) {
            return null;
        }
        if (type == TaskSearchType.TITLE)
            return task.title.containsIgnoreCase(param);
        else if (type == TaskSearchType.CAPTION)
            return task.caption.containsIgnoreCase(param);
        else
            return task.caption.containsIgnoreCase(param).or(task.title.likeIgnoreCase(param));
    }

    private BooleanExpression searchByStatus(TaskStatus status) {
        QTask task = QTask.task;
        if (status == TaskStatus.COMPLETE)
            return task.status.eq(TaskStatus.COMPLETE);
        else if (status == TaskStatus.TODO)
            return task.status.eq(TaskStatus.TODO);
        else
            return null;
    }
}
