package ru.job4j.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    private TaskService taskService;

    private PriorityService priorityService;

    private TaskController taskController;

    @BeforeEach
    public void initServices() {
        taskService = mock(TaskService.class);
        priorityService = mock(PriorityService.class);
        taskController = new TaskController(taskService, priorityService);
    }

    @Test
    public void whenRequestTaskListThenGetTableWithTasks() {
        var task = new Task(1, "description");
        var anotherTask = new Task(2, "anotherDescription");
        var expected = List.of(task, anotherTask);

        when(taskService.findAll()).thenReturn(expected);

        var model = new ConcurrentModel();
        User user = mock(User.class);
        var view = taskController.getAll(model);
        var actual = model.getAttribute("tasks");

        assertThat(view).isEqualTo("tasks/all");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenChooseActiveTasksThenGetActiveList() {
        var activeTask = new Task(1, "description");
        activeTask.setDone(true);
        var anotherActiveTask = new Task(2, "anotherDescription");
        anotherActiveTask.setDone(true);
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        var view = taskController.getActive(model);

        assertThat(view).isEqualTo("tasks/active");
    }

    @Test
    public void whenChooseClosedTasksThenGetClosedList() {
        var activeTask = new Task(1, "description");
        activeTask.setDone(true);
        var anotherActiveTask = new Task(2, "anotherDescription");
        anotherActiveTask.setDone(true);
        var taskList = List.of(activeTask, anotherActiveTask);

        when(taskService.findByStatus(any(Boolean.class))).thenReturn(taskList);

        var model = new ConcurrentModel();
        var view = taskController.getClosed(model);

        assertThat(view).isEqualTo("tasks/closed");
    }

    @Test
    public void whenDeleteTasksThenSuccessfullyDone() {
        var view = taskController.remove(1);
        assertThat(view).isEqualTo("redirect:/all-tasks");
    }

    @Test
    public void whenCompleteTasksThenSuccessfullyDone() {
        var view = taskController.complete(new Task());
        assertThat(view).isEqualTo("redirect:/all-tasks");
    }

    @Test
    public void whenAddNewTaskThenOK() {
        var task = new Task(1, "description");

        var model = new ConcurrentModel();
        User user = mock(User.class);
        Priority priority = mock(Priority.class);
        var view = taskController.create(model, task, user);

        assertThat(view).isEqualTo("redirect:/all-tasks");
    }
}