package com.example.store.signup.model;

import com.example.store.signup.entity.Store;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDescSearchRepository {

    private final EntityManager entityManager;

    public List<StoreDescSearch> getStoreDescSearch() {
        String sql = "SELECT ID, USER_ID, STORE_NAME, ADDRESS, PHONE, INFORMATION, CRE_DATE FROM STORE ORDER BY STORE_NAME";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();

        List<StoreDescSearch> resultList =
                jpaResultMapper.list(nativeQuery, StoreDescSearch.class);

        return resultList;
    }
}
