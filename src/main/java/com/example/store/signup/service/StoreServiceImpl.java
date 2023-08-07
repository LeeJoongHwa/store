package com.example.store.signup.service;

import com.example.store.signup.entity.SignUp;
import com.example.store.signup.entity.Store;
import com.example.store.signup.entity.StoreReview;
import com.example.store.signup.model.*;
import com.example.store.signup.repository.ReviewRepository;
import com.example.store.signup.repository.StoreRepository;
import com.example.store.signup.repository.StoreSignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreSignUpRepository storeSignUpRepository;
    private final StoreDescSearchRepository storeDescSearchRepository;
    private final ReviewRepository reviewRepository;
    private final StoreCountRepository storeCountRepository;

    @Override
    public ServiceResult addReview(long id, String userId, UserStoreReview userStoreReview) {

        Optional<SignUp> signUp = storeSignUpRepository.findByUserId(userId);
        if (!signUp.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }

        Optional<Store> storeOptional = storeRepository.findById(id);
        if (storeOptional.isEmpty()) {
            return ServiceResult.fail("매장 정보가 존재하지 않습니다.");
        }

        StoreReview storeReview = StoreReview.builder()
                .storeName(storeOptional.get().getStoreName())
                .review(userStoreReview.getReview())
                .rating(userStoreReview.getReviewType())
                .build();

        reviewRepository.save(storeReview);

        long reviewCount =  storeCountRepository.getStoreCount();
        long plusReviewCount = reviewCount + 1;

        double averageRating = storeCountRepository.getAverageRating();
        double updateAverageRating = ((reviewCount * averageRating) + storeReview.getRating().getValue()) / plusReviewCount;

        Store store = Store.builder()
                .stars(updateAverageRating)
                .build();

        storeRepository.save(store);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult reviseInfo(long id, StoreInfoInput storeInfoInput) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("수정할 매장 정보가 없습니다.");
        }

        Store store = optionalStore.get();
        store.setUserId(storeInfoInput.getUserId());
        store.setStoreName(storeInfoInput.getStoreName());
        store.setAddress(storeInfoInput.getAddress());
        store.setPhone(storeInfoInput.getPhone());
        store.setInformation(storeInfoInput.getInformation());
        store.setRevDate(LocalDateTime.now());

        storeRepository.save(store);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult addInformation(String userId, StoreInfoInput storeInfoInput) {

        StoreInfo storeInfo = storeRepository.findByStoreName(storeInfoInput.getStoreName());

        Optional<SignUp> signUp = storeSignUpRepository.findByUserId(userId);
        if (!signUp.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }

        if (storeInfo != null && storeInfoInput.getStoreName().equals(storeInfo.getStoreName())) {
            return ServiceResult.fail("이미 동일한 매장의 정보가 있습니다.");
        }

        Store store = Store.builder()
                .userId(userId)
                .storeName(storeInfoInput.getStoreName())
                .address(storeInfoInput.getAddress())
                .phone(storeInfoInput.getPhone())
                .information(storeInfoInput.getInformation())
                .creDate(LocalDateTime.now())
                .build();

        storeRepository.save(store);

        return ServiceResult.success();
    }

    @Override
    public List<Store> allSearch() {
        return storeRepository.findAll();
    }

    @Override
    public List<StoreDescSearch> descSearch() {
        return storeDescSearchRepository.getStoreDescSearch();
    }
}
