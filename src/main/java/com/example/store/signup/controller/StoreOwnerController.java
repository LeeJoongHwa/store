package com.example.store.signup.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.store.signup.entity.SignUp;
import com.example.store.signup.entity.Store;
import com.example.store.signup.exception.ExistUserException;
import com.example.store.signup.exception.NotPartnerUserException;
import com.example.store.signup.exception.PasswordNotMatchException;
import com.example.store.signup.exception.UserIdNotFoundException;
import com.example.store.signup.model.*;
import com.example.store.signup.repository.StoreRepository;
import com.example.store.signup.repository.StoreSignUpRepository;
import com.example.store.signup.service.StoreService;
import com.example.store.signup.util.JWTUtils;
import com.example.store.signup.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreSignUpRepository storeSignUpRepository;
    private final StoreRepository storeRepository;
    private final StoreService storeService;

    public String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    @PostMapping("/store/owner/signUp")
    public ResponseEntity<?> signUpOwner(@RequestBody @Valid OwnerSignUp ownerSignUp) {

        if (storeSignUpRepository.countByUserId(ownerSignUp.getUserId()) > 0) {
            throw new ExistUserException("이미 존재하는 ID입니다");
        }

        String encryptPassword = getEncryptPassword(ownerSignUp.getPassword());

        SignUp signUp = SignUp.builder()
                .userId(ownerSignUp.getUserId())
                .password(encryptPassword)
                .username(ownerSignUp.getUsername())
                .creDate(LocalDateTime.now())
                .partnerYn(true)
                .build();

        storeSignUpRepository.save(signUp);

        return ResponseEntity.ok().body(signUp);
    }

    @PostMapping("/store/owner/login")
    public ResponseEntity<?> loginOwner(@RequestBody @Valid OwnerLogin ownerLogin) {

        SignUp signUp = storeSignUpRepository.findByUserId(ownerLogin.getUserId())
                .orElseThrow(() -> new UserIdNotFoundException("사용자 아이디가 틀렸습니다."));

        if (!PasswordUtils.equalPassword(ownerLogin.getPassword(), signUp.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다");
        }

        String token = JWT.create()
                .withClaim("user_id", signUp.getId())
                .withSubject(signUp.getUsername())
                .withIssuer(signUp.getUserId())
                .sign(Algorithm.HMAC512("store".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder()
                .token(token).build());
    }

    //점장인지 확인하기 위해 PartnerYn이 true인 것을 확인해야함.
    //근데 어떻게 하는지 모르겠네... 흠.....
    @PostMapping("/store/information")
    public ResponseEntity<?> saveStoreInfo(@RequestHeader("F-TOKEN") String token
            , @RequestBody @Valid StoreInfoInput storeInfoInput) {

        String userId = "";

        try {
            userId = JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

//        if (signUp.) {
//            throw new NotPartnerUserException("점장 회원이 아닙니다.");
//        }

        ServiceResult result = storeService.addInformation(userId, storeInfoInput);

        return ResponseResult.result(result);
    }

    @PutMapping("/store/{id}/information/revise")
    public ResponseEntity<?> reviseStoreInfo(@PathVariable long id, @RequestBody StoreInfoInput storeInfoInput) {

        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = storeService.reviseInfo(id, storeInfoInput);

        if(!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }
}
