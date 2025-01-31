package com.example.todolist.controller;

import com.example.todolist.dto.TodoForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/todo")
public class TodoListController {
    @PostMapping("/new")
    public String createTodoList(TodoForm form) {
        // DTO 를 엔티티로 변환

        // 리파지터리로 엔티티를 DB에 저장
    }

}
