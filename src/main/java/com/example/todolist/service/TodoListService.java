package com.example.todolist.service;

import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
import com.example.todolist.repository.JdbcTemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    private final JdbcTemplateRepository jdbcTemplateRepository;

    public TodoListService(JdbcTemplateRepository jdbcTemplateRepository) {
        this.jdbcTemplateRepository = jdbcTemplateRepository;
    }

    public TodoResponseDto saveTodo(TodoRequestDto dto) {
        Todo todo = new Todo(dto.getTodo(), dto.getUsername(), dto.getPassword());

        return jdbcTemplateRepository.save(todo);
    }

    public List<TodoResponseDto> findAll(String modifiedAt, String author) {
        return jdbcTemplateRepository.findAll(modifiedAt, author);
    }

    public TodoResponseDto findById(Long id) {
        Optional<Todo> optional = jdbcTemplateRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 " + id + "값 입니다.");
        }
        return new TodoResponseDto(optional.get());
    }

    @Transactional
    public TodoResponseDto update(Long id, String todo, String username, String password) {
        if (todo == null || todo.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "할 일 입력은 필수 입니다.");
        }

        int update = jdbcTemplateRepository.update(id, todo, username, password);

        if (update == 0) {
            throw new IllegalArgumentException("비밀번호가 틀렸거나 해당 ID의 할 일이 존재하지 않습니다.");
        }

        Optional<Todo> optional = jdbcTemplateRepository.findById(id);

        return new TodoResponseDto(optional.get());
    }

    public void delete(Long id, String password) {
        jdbcTemplateRepository.delete(id, password);
    }
}
