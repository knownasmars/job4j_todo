package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todo_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
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