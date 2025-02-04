package com.example.todolist.repository;

import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    public TodoResponseDto save(Todo todo);
    public List<TodoResponseDto> findAll(String modifiedAt, String username);
    public Optional<Todo> findById(Long id);
    public void delete(Long id, String password);
    public int update(Long id, String todo, String username, String password);
}
