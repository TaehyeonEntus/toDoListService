package com.taehyeon.toDoListService.controller;

import com.taehyeon.toDoListService.annotation.AfterLogin;
import com.taehyeon.toDoListService.domain.*;
import com.taehyeon.toDoListService.domain.dto.HomeDisplayRequest;
import com.taehyeon.toDoListService.domain.dto.TaskAddRequest;
import com.taehyeon.toDoListService.domain.dto.TaskEditRequest;
import com.taehyeon.toDoListService.exception.authException.AuthException;
import com.taehyeon.toDoListService.service.MemberServiceImpl;
import com.taehyeon.toDoListService.service.TaskServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@AfterLogin
public class HomeController {
    private final MemberServiceImpl memberService;
    private final TaskServiceImpl taskService;

    @GetMapping("/home")
    public String home(@ModelAttribute TaskSearchCondition condition,
                       @PageableDefault(size = 10, sort = "dueDate",direction = Sort.Direction.ASC) Pageable pageable,
                       Model model,
                       HttpSession session) {
        try{
            memberService.find((Long) session.getAttribute("memberId"));

            Page<Task> page = taskService.findPage(pageable, condition);

            model.addAttribute("condition", condition);
            model.addAttribute("homeDisplayRequest", new HomeDisplayRequest(page));
        } catch (AuthException e){
            session.invalidate();

            return "redirect:/";
        }

        return "home";
    }

    @GetMapping("/home/add")
    public String addTask(Model model) {
        model.addAttribute("taskAddRequest", new TaskAddRequest());

        return "taskAdd";
    }

    @PostMapping("/home/add")
    public String addTask(@Valid TaskAddRequest taskAddRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "taskAdd";
        }
        Member member = memberService.find((Long)session.getAttribute("memberId"));
        Task task = Task.createTask(taskAddRequest.getTitle(), taskAddRequest.getCaption(), taskAddRequest.getDueDate());

        task.assignTo(member);
        taskService.add(task);
        return "redirect:/home";
    }

    @GetMapping("/home/{taskId}/edit")
    public String editTask(Model model, @PathVariable Long taskId) {
        Task task = taskService.find(taskId);

        model.addAttribute("taskEditRequest", new TaskEditRequest(task));
        return "taskEdit";
    }

    @PostMapping("/home/{taskId}/edit")
    public String editTask(@Valid TaskEditRequest taskEditRequest, BindingResult bindingResult, @PathVariable Long taskId) {
        if (bindingResult.hasErrors()) {
            return "taskEdit";
        }
        taskService.update(taskId, Task.createTask(taskEditRequest.getTitle(), taskEditRequest.getCaption(), taskEditRequest.getDueDate()).changeStatus(taskEditRequest.getStatus()));
        return "redirect:/home";
    }

    @PostMapping("/home/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId) {
        taskService.updateStatus(taskId, TaskStatus.COMPLETE);
        return "redirect:/home";
    }

    @PostMapping("/home/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
