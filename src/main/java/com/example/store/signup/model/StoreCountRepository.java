package com.example.store.signup.model;

import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class StoreCountRepository {

    private final EntityManager entityManager;

    public Long getStoreCount() {
        String sql = "SELECT COUNT(*) AS total FROM STORE";

        Query nativeQuery = entityManager.createNativeQuery(sql);

        Object result = nativeQuery.getSingleResult();
        Long totalCount = ((Number) result).longValue();

        return totalCount;
    }

    public double getAverageRating() {
        String sql = "SELECT AVG(rating) FROM StoreReview"; // StoreReview는 실제 테이블 이름으로 변경

        Query query = entityManager.createQuery(sql);

        Double averageRating = (Double) query.getSingleResult();

        if (averageRating != null) {
            return averageRating;
        } else {
            return 0.0; // 평균 값이 없는 경우 0.0을 반환하거나 다른 처리를 수행할 수 있습니다.
        }
    }
}
