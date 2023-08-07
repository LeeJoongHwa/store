package com.example.store.signup.model;

import lombok.*;
import org.hibernate.internal.build.AllowSysOut;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerLogin {

    @NotBlank(message = "아이디는 필수 항목 입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 항목 입니다.")
    private String password;

}
