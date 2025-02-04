package com.example.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private String todo;
    private String name;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public Todo(String todo, String name, String password) {
        this.todo = todo;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
