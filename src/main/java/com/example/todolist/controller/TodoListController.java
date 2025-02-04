package com.example.todolist.controller;

import com.example.todolist.dto.TodoRequestDto;
import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
public class TodoListController {
    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/new")
    public ResponseEntity<TodoResponseDto> createTodoList(@RequestBody TodoRequestDto dto) {
        return new ResponseEntity<>(todoListService.saveTodo(dto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> findAllTodoList(
            @RequestParam(required = false) String modifiedAt,
            @RequestParam(required = false) String username
    ) {
        List<TodoResponseDto> todoList = todoListService.findAll(modifiedAt, username);
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(todoListService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> update(@PathVariable Long id, @RequestBody TodoRequestDto dto) {
        return new ResponseEntity<>(todoListService.update(id, dto.getTodo(), dto.getUsername(), dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody TodoRequestDto dto) {
        todoListService.delete(id, dto.getPassword());
        return ResponseEntity.noContent().build(); // 204 반환
    }

}