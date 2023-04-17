package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.List;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private final PriorityStore priorityStore;

    @Override
    public List<Priority> findAll() {
        return priorityStore.findAll();
    }
}
