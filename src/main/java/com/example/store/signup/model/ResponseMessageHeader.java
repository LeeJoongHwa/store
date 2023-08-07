package com.example.store.signup.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessageHeader {

    private boolean result;
    private String resultCode;
    private String message;
    private int status;

}
