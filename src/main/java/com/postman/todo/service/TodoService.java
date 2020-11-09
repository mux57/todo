package com.postman.todo.service;

import com.postman.todo.model.Todo;
import com.postman.todo.model.TodoDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {
  private Map<String, List<Todo>> todos = new HashMap<>();

  private int getNextId(String topic) {
    return todos.get(topic).size() + 1;
  }

  public Todo create(TodoDto todoDto) {
    Todo.builder().isCompleted(true).build();
    if (!todos.containsKey(todoDto.getTopic())) {
      todos.put(todoDto.getTopic(), new ArrayList<>());
    }
    int id = getNextId(todoDto.getTopic());
    Todo todo = Todo.builder().id(id).topic(todoDto.getTopic())
        .description(todoDto.getDescription()).name(todoDto.getName())
        .isCompleted(true).build();
    todos.get(todo.getTopic()).add(todo);
    return todo;
  }

  public Map<String, List<Todo>> getTodos() {
    return todos;
  }

  public Optional<Todo> getTodo(int id) {
    if (todos.isEmpty()) {
      return Optional.empty();
    }
    for (Map.Entry<String, List<Todo>> entry : todos.entrySet()) {
      for (Todo todo : entry.getValue()) {
        if (todo.getId() == id) {
          return Optional.of(todo);
        }
      }
    }
    return Optional.empty();
  }

  public Optional<Todo> delete(int id) {
    Optional<Todo> todo = getTodo(id);
    if (!todo.isPresent()) {
      return todo;
    }
    todos.get(todo.get().getTopic()).remove(todo);
    return todo;
  }

  public Optional<Todo> update(TodoDto dto, int id) {
    for (Map.Entry<String, List<Todo>> entry : todos.entrySet()) {
      for (Todo todo : entry.getValue()) {
        if (todo.getId() == id) {
          if (StringUtils.hasLength(dto.getDescription())) {
            todo.setDescription(dto.getDescription());
          }
          if (StringUtils.hasLength(dto.getName())) {
            todo.setName(dto.getName());
          }
          return Optional.of(todo);
        }
      }
    }
    return Optional.empty();
  }

  public Optional<Map<String, List<Todo>>> reOrderTodo(Todo todo, int currIndex, int nextIndex) {
    List<Todo> listTodo = todos.get(todo.getTopic());
    Todo cur = null;
    Todo next = null;

    for (int i = 0; i < listTodo.size(); i++) {
      if (i + 1 == currIndex) {
        cur = listTodo.get(i);
      }
      if (i + 1 == nextIndex) {
        next = listTodo.get(i);
      }
      if (cur != null && next != null) {
        break;
      }
    }
    if (cur == null || next == null) {
      return Optional.empty();
    }
    listTodo.set(currIndex - 1, next);
    listTodo.set(nextIndex - 1, cur);
    todos.put(todo.getTopic(), listTodo);
    return Optional.of(todos);
  }
}
