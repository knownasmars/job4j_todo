package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;

    @NonNull
    private String description;

    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now();

    @EqualsAndHashCode.Exclude
    private boolean done = false;
}
