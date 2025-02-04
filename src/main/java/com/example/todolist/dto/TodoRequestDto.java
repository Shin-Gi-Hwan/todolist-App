package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoRequestDto {
    private String username;
    private String todo;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
