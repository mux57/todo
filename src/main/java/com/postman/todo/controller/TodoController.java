package com.postman.todo.controller;

import com.postman.todo.model.Todo;
import com.postman.todo.model.TodoDto;
import com.postman.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/todos")
public class TodoController {
  @Autowired
  private TodoService todoService;

  @PostMapping(value = "")
  public ResponseEntity<?> create(@RequestBody TodoDto todoDto) {
    // validation call
    Todo todo = todoService.create(todoDto);
    return ResponseEntity.ok().body(todo);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> fetchTodo(@PathVariable("id") int id) {
    Optional<Todo> todoOptional = todoService.getTodo(id);

    if (!todoOptional.isPresent()) {
      return new ResponseEntity<>(NOT_FOUND);
    }

    return ResponseEntity.ok(todoOptional.get());
  }

  @GetMapping(value = "")
  private ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(todoService.getTodos());
  }

  @DeleteMapping(value = "/{id}")
  private ResponseEntity<?> delete(@PathVariable("id") int id) {
    Optional<Todo> todo = todoService.getTodo(id);
    if (!todo.isPresent()) {
      return new ResponseEntity<>(NOT_FOUND);
    }
    return ResponseEntity.ok().body("Successfully deleted");
  }

  @PutMapping(value = "/{id}")
  private ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody TodoDto todoDto) {
    if (!todoService.getTodo(id).isPresent()) {
      return new ResponseEntity<>(NOT_FOUND);
    }
    final Optional<Todo> todo = todoService.update(todoDto, id);
    return ResponseEntity.ok(todo.get());
  }

  @PutMapping(value = "/{id}/reOrder")
  public ResponseEntity<?> reorder(@PathVariable("id") int id, @RequestParam("curIndex") int curIndex, @RequestParam("nextIndex") int nextIndex) {
    Optional<Todo> todo = todoService.getTodo(id);
    if (!todo.isPresent()) {
      return new ResponseEntity<>(NOT_FOUND);
    }
    return ResponseEntity.ok().body(todoService.reOrderTodo(todo.get(), curIndex, nextIndex));
  }
}
