package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "auto_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "login", "login",
            "password", "password"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String name;
    @NonNull
    private String login;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String password;
}