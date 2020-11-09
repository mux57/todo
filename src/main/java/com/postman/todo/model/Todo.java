package com.postman.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class Todo {
  private int id;
  private String topic;
  private String name;
  private String description;
  private boolean isCompleted;
}
