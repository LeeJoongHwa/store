package com.example.store.signup.model;

import lombok.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDescSearch {

    private long id;
    private String userId;
    private String storeName;
    private String address;
    private String phone;
    private String information;
    private LocalDateTime creDate;

    public StoreDescSearch(Object[] arrObj) {
        this.id = ((BigInteger) arrObj[0]).longValue();
        this.userId = (String)arrObj[1];
        this.storeName = (String)arrObj[2];
        this.address = (String)arrObj[3];
        this.phone = (String)arrObj[4];
        this.information = (String)arrObj[5];
        this.creDate = ((Timestamp) arrObj[6]).toLocalDateTime();
    }

    public StoreDescSearch(BigInteger id, String userId, String storeName, String address, String phone, String information, Timestamp creDate){
        this.id = id.longValue();
        this.userId = userId;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.information = information;
        this.creDate = creDate.toLocalDateTime();
    }
}
