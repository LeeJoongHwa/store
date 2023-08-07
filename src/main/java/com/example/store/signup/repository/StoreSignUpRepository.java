package com.example.store.signup.repository;

import com.example.store.signup.entity.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreSignUpRepository extends JpaRepository<SignUp, Long> {

    Optional<SignUp> findByUserId(String userId);

    int countByUserId(String userId);

}
