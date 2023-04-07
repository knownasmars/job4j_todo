package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    TaskStore taskStore;

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
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
    public boolean complete(Task task) {
        return taskStore.complete(task);
    }
}