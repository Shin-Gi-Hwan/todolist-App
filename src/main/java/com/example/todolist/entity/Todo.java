package com.example.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private String todo;
    private User name;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public Todo(String todo, User name, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.todo = todo;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
