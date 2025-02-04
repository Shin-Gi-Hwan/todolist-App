package com.example.todolist.dto;


import com.example.todolist.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String todo;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.todo = todo.getTodo();
        this.username = todo.getName();
        this.password = todo.getPassword();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }

    public TodoResponseDto(Long id, String todo, LocalDateTime now) {
    }
}
