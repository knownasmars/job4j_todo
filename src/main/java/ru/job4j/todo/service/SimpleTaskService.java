package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public void deleteById(int id) {
        taskStore.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public List<Task> findByStatus(boolean status) {
        return taskStore.findByStatus(status);
    }

    @Override
    public void complete(Task task) {
        taskStore.complete(task);
    }
}