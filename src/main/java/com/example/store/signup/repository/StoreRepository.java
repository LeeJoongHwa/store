package com.example.store.signup.repository;

import com.example.store.signup.entity.Store;
import com.example.store.signup.model.StoreInfo;
import com.example.store.signup.model.StoreInfoInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    StoreInfo findByStoreName(String storeName);
}
