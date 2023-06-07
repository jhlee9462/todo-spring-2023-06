package com.example.todo.todoapi.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TodoModifyRequestDTO {

    private String id;
    private boolean done;

}
