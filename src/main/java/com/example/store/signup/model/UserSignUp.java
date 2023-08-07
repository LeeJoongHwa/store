package com.example.store.signup.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUp {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String username;

    @NotBlank(message = "아이디는 필수 항목입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 항목입니다")
    private String password;

}
