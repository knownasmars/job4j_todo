package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserStore userStore;

    public SimpleUserService(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
