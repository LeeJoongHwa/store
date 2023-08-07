package com.example.store.signup.repository;

import com.example.store.signup.entity.SignUp;
import com.example.store.signup.entity.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<StoreReview, Long> {

}
