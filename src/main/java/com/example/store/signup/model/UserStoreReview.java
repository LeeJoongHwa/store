package com.example.store.signup.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStoreReview {

    private StarsType reviewType;
    private String review;
}
