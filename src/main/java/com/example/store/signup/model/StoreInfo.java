package com.example.store.signup.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfo {

    private long id;

    @NotBlank(message = "매장 이름은 필수 항목입니다.")
    private String userId;
    private String storeName;
    private String address;
    private String information;


}
