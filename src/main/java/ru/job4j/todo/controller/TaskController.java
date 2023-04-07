package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping({"/","/ex1-tabs-1"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/ex1-tabs-1", "/tasks"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("/ex1-tabs-2")
    public String getActive(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/active";
    }

    @GetMapping("/ex1-tabs-3")
    public String getClosed(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/closed";
    }

    @PostMapping({"/", "/ex1-tabs-1"})
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/ex1-tabs-1";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/ex1-tabs-1/delete/{id}")
    public String removeAll(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "The task with specified \"id\" is not found!");
            return "errors/404";
        }
        return "redirect:/ex1-tabs-1";
    }

    @GetMapping("/ex1-tabs-1/complete/{id}")
    public String completeAll(@ModelAttribute Task task, Model model, @PathVariable int id) {
        var isCompleted = taskService.complete(task);
        if (!isCompleted) {
            model.addAttribute("message", "The task with specified \"id\" is not found!");
            return "errors/404";
        }
        return "redirect:/ex1-tabs-1";
    }
}
