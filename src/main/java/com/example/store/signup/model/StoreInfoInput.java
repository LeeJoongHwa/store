package com.example.store.signup.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfoInput {

    @NotBlank(message = "매장 이름은 필수 항목입니다.")
    private String storeName;
    private String userId;
    private String address;
    private String phone;
    private String information;
    private LocalDateTime creDate;
    private LocalDateTime revDate;

}
