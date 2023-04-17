package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final PriorityService priorityService;

    @GetMapping({"/", "/all-tasks"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/all";
    }

    @GetMapping("/active-tasks")
    public String getActive(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/active";
    }

    @GetMapping("/closed-tasks")
    public String getClosed(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/closed";
    }

    @PostMapping({"/", "/all-tasks"})
    public String create(Model model, @ModelAttribute Task task,
                         @SessionAttribute User user) {
        model.addAttribute("user", user);
        task.setUser(user);
        taskService.save(task);
        return "redirect:/all-tasks";
    }

    @GetMapping("/all-tasks/delete/{id}")
    public String remove(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/all-tasks";
    }

    @GetMapping("/all-tasks/complete/{id}")
    public String complete(@ModelAttribute Task task) {
        taskService.complete(task);
        return "redirect:/all-tasks";
    }
}
