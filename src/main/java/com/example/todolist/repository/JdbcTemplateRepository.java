package com.example.todolist.repository;

import com.example.todolist.dto.TodoResponseDto;
import com.example.todolist.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class JdbcTemplateRepository implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TodoResponseDto save(Todo todo) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("todo").usingGeneratedKeyColumns("id"); // id 값 가동 증감

        Map<String, Object> params = new HashMap<>();
        params.put("todo", todo.getTodo());
        params.put("username", todo.getName());
        params.put("password", todo.getPassword());
        params.put("createdAt", java.sql.Date.valueOf(todo.getCreatedAt().toLocalDate()));
        params.put("modifiedAt", java.sql.Date.valueOf(todo.getModifiedAt().toLocalDate()));

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new TodoResponseDto(key.longValue(), todo.getTodo(), todo.getName(), todo.getPassword(), todo.getCreatedAt(), todo.getModifiedAt());
    }

    @Override
    public List<TodoResponseDto> findAll(String modifiedAt, String username) {

        String sql = "SELECT * FROM TODO";
        List<Object> params = new ArrayList<>();

        List<String> conditions = new ArrayList<>();
        if (modifiedAt != null && !modifiedAt.trim().isEmpty()) {
            conditions.add("DATE(MODIFIED_AT) = ?");
            params.add(LocalDate.parse(modifiedAt));
        }
        if (username != null && !username.trim().isEmpty()) {
            conditions.add("USERNAME = ?");
            params.add(username);
        }

        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        sql += " ORDER BY MODIFIED_AT DESC";

        return jdbcTemplate.query(sql, params.toArray(), todoRowMapper());
    }

    @Override
    public Optional<Todo> findById(Long id) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM TODO WHERE ID = ?", todoRowMapper2(), id);
        return result.stream().findAny();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM TODO WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
    }

    @Override
    public int update(Long id, String todo, String username, String password) {
        return jdbcTemplate.update("UPDATE TODO SET TODO = ?, USERNAME = ?, MODIFIED_AT = ? WHERE id = ? AND PASSWORD = ?", todo, username, LocalDateTime.now(), id, password);
    }

    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("created_At").toLocalDateTime(),
                        rs.getTimestamp("modified_At").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Todo> todoRowMapper2() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("created_At").toLocalDateTime(),
                        rs.getTimestamp("modified_At").toLocalDateTime()
                );
            }
        };
    }
}
