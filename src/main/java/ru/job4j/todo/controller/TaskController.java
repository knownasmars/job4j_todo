package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/all-tasks"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("/active-tasks")
    public String getActive(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/active";
    }

    @GetMapping("/closed-tasks")
    public String getClosed(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/closed";
    }

    @PostMapping({"/", "/all-tasks"})
    public String create(@ModelAttribute Task task, Model model) {
        taskService.save(task);
        return "redirect:/all-tasks";
    }

    @GetMapping("/all-tasks/delete/{id}")
    public String remove(Model model, @PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/all-tasks";
    }

    @GetMapping("/all-tasks/complete/{id}")
    public String complete(@ModelAttribute Task task, Model model, @PathVariable int id) {
        taskService.complete(task);
        return "redirect:/all-tasks";
    }
}
