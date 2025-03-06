package com.taehyeon.toDoListService.controller;

import com.taehyeon.toDoListService.domain.Member;
import com.taehyeon.toDoListService.domain.Task;
import com.taehyeon.toDoListService.domain.dto.HomeDisplayRequest;
import com.taehyeon.toDoListService.domain.dto.TaskAddRequest;
import com.taehyeon.toDoListService.domain.dto.TaskDTO;
import com.taehyeon.toDoListService.exception.authException.AuthException;
import com.taehyeon.toDoListService.service.MemberServiceImpl;
import com.taehyeon.toDoListService.service.TaskServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

            model.addAttribute("homeDisplayRequest", new HomeDisplayRequest(member.getNickname(), tasks));
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
    public String addTask(@ModelAttribute("taskAddRequest") TaskAddRequest taskAddRequest, HttpSession session) {
        Member member = memberService.findByUsername((String)session.getAttribute("username"));
        Task task = new Task(taskAddRequest);

        task.assignTo(member);
        taskService.add(task);

        return "redirect:/home";
    }

    @GetMapping("/{taskId}")
    public String showTask(Model model, @PathVariable String taskId) {
        model.addAttribute("taskDTO", new TaskDTO());

        return "taskShow";
    }

    @GetMapping("/{taskId}/edit")
    public String editTask(Model model, @PathVariable Long taskId) {
        Task task = taskService.find(taskId);

        model.addAttribute("taskDTO", new TaskDTO(task));

        return "taskEdit";
    }

    @PostMapping("/{taskId}/edit")
    public String editTask(@ModelAttribute("taskDTO") TaskDTO taskDTO) {
        Task task = taskService.find(taskDTO.getId());

        task.changeTitle(taskDTO.getTitle());
        task.changeCaption(taskDTO.getCaption());
        task.changeDueDate(taskDTO.getDueDate());
        task.changeStatus(taskDTO.getStatus());

        return "redirect:/home";
    }
}
