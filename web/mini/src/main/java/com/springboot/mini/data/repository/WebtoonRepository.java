package com.springboot.mini.data.repository;
// 리포지토리는 엔티티가 생성한 DB에 접근하는 데 사용되는 인터페이스

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.Webtoon;

public interface WebtoonRepository extends JpaRepository<Webtoon, String>{
    // @Query("SELECT t FROM webtoon t WHERE t.WebtoonId = :webtoonId")
    // List<Webtoon> queryByWebtoonId(Integer webtoonId);
    // @Query("SELECT w FROM webtoon w")
    // ArrayList<Webtoon> queryDataAll();

    @Override
    ArrayList<Webtoon> findAll();
    
    // 검색 기능
    @Query("SELECT t FROM Webtoon t WHERE t.webtoonId like %:every% or t.title like %:every% or t.hashTag like %:every%")
    List<Webtoon> queryByEvery(String every);
}
