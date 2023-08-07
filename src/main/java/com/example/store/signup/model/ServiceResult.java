package com.example.store.signup.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResult {

    private boolean result;
    private String message;

    public static ServiceResult fail(String message) {
        return ServiceResult.builder()
                .result(false)
                .message(message)
                .build();

    }

    public static ServiceResult success() {
        return ServiceResult.builder()
                .result(false)
                .build();
    }

    public boolean isFail() {
        return !result;
    }
}
