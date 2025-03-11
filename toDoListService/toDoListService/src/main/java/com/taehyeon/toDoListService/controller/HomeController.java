package com.taehyeon.toDoListService.controller;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.domain.TaskStatus;
import com.taehyeon.toDoListService.domain.dto.HomeDisplayRequest;
import com.taehyeon.toDoListService.domain.dto.TaskAddRequest;
import com.taehyeon.toDoListService.domain.dto.TaskEditRequest;
import com.taehyeon.toDoListService.exception.authException.AuthException;
import com.taehyeon.toDoListService.service.MemberServiceImpl;
import com.taehyeon.toDoListService.service.TaskServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final MemberServiceImpl memberService;
    private final TaskServiceImpl taskService;
    @GetMapping
    public String home(Model model, HttpSession session) {
        try{
            Member member = memberService.findByUsername((String) session.getAttribute("username"));
            List<Task> tasks = member.getTasks();

            model.addAttribute("homeDisplayRequest", new HomeDisplayRequest(tasks));
        } catch (AuthException e){
            session.invalidate();

            return "redirect:/login";
        }

        return "home";
    }

    @GetMapping("/add")
    public String addTask(Model model) {
        model.addAttribute("taskAddRequest", new TaskAddRequest());

        return "taskAdd";
    }

    @PostMapping("/add")
    public String addTask(@Valid TaskAddRequest taskAddRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "taskAdd";
        }
        Member member = memberService.findByUsername((String)session.getAttribute("username"));
        Task task = Task.createTask(taskAddRequest.getTitle(), taskAddRequest.getCaption(), taskAddRequest.getDueDate());

        task.assignTo(member);
        taskService.add(task);

        return "redirect:/home";
    }

    @GetMapping("/{taskId}/edit")
    public String editTask(Model model, @PathVariable Long taskId) {
        Task task = taskService.find(taskId);

        model.addAttribute("taskEditRequest", new TaskEditRequest(task));
        return "taskEdit";
    }

    @PostMapping("/{taskId}/edit")
    public String editTask(@Valid TaskEditRequest taskEditRequest, BindingResult bindingResult, @PathVariable Long taskId) {
        if (bindingResult.hasErrors()) {
            return "taskEdit";
        }
        Task task = taskService.find(taskId);

        task.changeTitle(taskEditRequest.getTitle());
        task.changeCaption(taskEditRequest.getCaption());
        task.changeDueDate(taskEditRequest.getDueDate());

        taskService.update(task);
        return "redirect:/home";
    }

    @PostMapping("/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId) {
        Task task = taskService.find(taskId);
        task.changeStatus(TaskStatus.COMPLETE);
        taskService.update(task);
        return "redirect:/home";
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);

        return "redirect:/home";
    }
}
