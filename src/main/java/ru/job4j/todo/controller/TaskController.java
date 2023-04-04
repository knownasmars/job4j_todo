package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/ex1-tabs-1", "/#ex1-tabs-1"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping({"#active", "/ex1-tabs-2", "/#ex1-tabs-2"})
    public String getActive(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/active";
    }

    @GetMapping({"#closed", "/ex1-tabs-3", "/#ex1-tabs-3"})
    public String getClosed(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/closed";
    }

    @PostMapping({"/", "/ex1-tabs-1", "/#ex1-tabs-1"})
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/ex1-tabs-1";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
