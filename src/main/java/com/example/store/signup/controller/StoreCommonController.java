package com.example.store.signup.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.store.signup.entity.Store;
import com.example.store.signup.model.*;
import com.example.store.signup.repository.StoreRepository;
import com.example.store.signup.service.StoreService;
import com.example.store.signup.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreCommonController {

    private final StoreService storeService;

    @GetMapping("/store/all/search")
    public ResponseEntity<?> storeAllSearch() {
        List<Store> StoreList = storeService.allSearch();

        return ResponseEntity.ok().body(ResponseMessage.success(StoreList));
    }

    @GetMapping("/store/desc/search")
    public ResponseEntity<?> storeDescSearch() {
        List<StoreDescSearch> StoreList = storeService.descSearch();

        return ResponseEntity.ok().body(ResponseMessage.success(StoreList));
    }

    @PostMapping("/store/user/{id}/review")
    public ResponseEntity<?> storeReview(@PathVariable long id
            ,@RequestHeader("F-TOKEN") String token
    , @RequestBody UserStoreReview userStoreReview){

        String userId = "";

        try {
            userId = JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = storeService.addReview(id, userId, userStoreReview);
        return ResponseResult.success(result);
    }
}
