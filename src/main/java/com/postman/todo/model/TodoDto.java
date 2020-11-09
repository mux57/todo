package com.postman.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
  private int id;
  @NonNull
  private String topic;
  @NonNull
  private String name;
  private String description;
  private Boolean isCompleted;
}
