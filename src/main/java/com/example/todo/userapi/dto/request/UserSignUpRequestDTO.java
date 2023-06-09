package com.example.todo.userapi.dto.request;

import com.example.todo.userapi.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 5)
    private String userName;

    private String profileImage;

    // 엔터티로 변경하는 메서드
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .build();
    }

    public User toEntity(String uploadFilePath) {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .profileImg(uploadFilePath)
                .build();
    }

}
