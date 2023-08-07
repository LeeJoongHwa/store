package com.example.store.signup.entity;

import com.example.store.signup.model.StarsType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String storeName;
    private StarsType rating;
    private String review;
    private LocalDateTime regDate;

}
