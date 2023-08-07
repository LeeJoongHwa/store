package com.example.store.signup.service;

import com.example.store.signup.entity.Store;
import com.example.store.signup.model.*;

import java.util.List;

public interface StoreService {
    ServiceResult addReview(long id, String userId, UserStoreReview userStoreReview);

    ServiceResult reviseInfo(long id, StoreInfoInput storeInfoInput);

    ServiceResult addInformation(String userId, StoreInfoInput storeInfoInput);

    List<Store> allSearch();

    List<StoreDescSearch> descSearch();
}
