package com.example.store.signup.repository;

import com.example.store.signup.entity.Rating;
import com.example.store.signup.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRatingSearchRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByStore(Store store);
}
