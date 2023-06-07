package com.example.todo.todoapi.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TodoListResponseDTO {

    private String error; // 에러 발생시 에러메세지를 담을 필드
    private List<TodoDetailResponseDTO> todos;

}
