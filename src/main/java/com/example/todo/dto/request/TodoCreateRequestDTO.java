package com.example.todo.dto.request;

import com.example.todo.entity.Todo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title;

    // dto를 엔터티로 변환하는 메서드
    public Todo toEntity() {
        return Todo.builder()
                .title(this.title)
                .build();
    }
}
