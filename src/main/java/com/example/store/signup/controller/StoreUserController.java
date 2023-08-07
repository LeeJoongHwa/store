package com.example.store.signup.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.store.signup.entity.SignUp;
import com.example.store.signup.entity.Store;
import com.example.store.signup.exception.ExistUserException;
import com.example.store.signup.exception.PasswordNotMatchException;
import com.example.store.signup.exception.StoreNotFoundException;
import com.example.store.signup.exception.UserIdNotFoundException;
import com.example.store.signup.model.*;
import com.example.store.signup.repository.StoreRepository;
import com.example.store.signup.repository.StoreSignUpRepository;
import com.example.store.signup.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class StoreUserController {

    private final StoreSignUpRepository storeSignUpRepository;
    private final StoreRepository storeRepository;

    public String getEncryptPassword (String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    @PostMapping("/store/user/signUp")
    public ResponseEntity<?> signUpUser(@RequestBody UserSignUp userSignUp) {

        if(storeSignUpRepository.countByUserId(userSignUp.getUserId()) > 0){
            throw new ExistUserException("이미 존재하는 ID입니다.");
        }

        String encryptPassword = getEncryptPassword(userSignUp.getPassword());

        SignUp signUp = SignUp.builder()
                .username(userSignUp.getUsername())
                .userId(userSignUp.getUserId())
                .password(encryptPassword)
                .partnerYn(false)
                .creDate(LocalDateTime.now())
                .build();

        storeSignUpRepository.save(signUp);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/store/user/login")
    public ResponseEntity<?> loginOwner(@RequestBody @Valid UserLogin userLogin) {

        SignUp signUp = storeSignUpRepository.findByUserId(userLogin.getUserId())
                .orElseThrow(() -> new UserIdNotFoundException("사용자 아이디가 틀렸습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), signUp.getPassword())) {
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

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<?> StoreNotFoundExceptionHandler(StoreNotFoundException storeNotFoundException){
        return new ResponseEntity<>(storeNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/store/user/{id}/information")
    public StoreResponse searchStore(@PathVariable long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("매장 정보가 없습니다."));

        StoreResponse storeResponse = StoreResponse.of(store);

        return storeResponse;
    }
}
