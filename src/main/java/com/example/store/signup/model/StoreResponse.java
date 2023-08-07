package com.example.store.signup.model;

import com.example.store.signup.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NotBlank
@Builder
public class StoreResponse {

    @NotBlank(message = "매장 이름은 필수 항목입니다.")
    private String storeName;
    private String address;
    private String information;

    public static StoreResponse of(Store store) {
        return StoreResponse.builder()
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .information(store.getInformation())
                .build();
    }
}
