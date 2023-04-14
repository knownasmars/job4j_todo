package ru.job4j.todo.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import org.sql2o.Sql2o;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
public class SimpleUserStore implements UserStore {

    private static final Logger LOG = LoggerFactory.getLogger(
            SimpleUserStore.class.getName());

    private final Sql2o sql2o;

    public SimpleUserStore(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                  INSERT INTO todo_user(name, login, password)
                  VALUES (:name, :login, :password)
                  """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", user.getName())
                    .addParameter("login", user.getLogin())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error("Пользователь с такими данными уже существует", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM todo_user WHERE login = :login and password = :password;"
            );
            query.addParameter("login", login);
            query.addParameter("password", password);
            var user =
                    query.setColumnMappings(User.COLUMN_MAPPING)
                            .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}