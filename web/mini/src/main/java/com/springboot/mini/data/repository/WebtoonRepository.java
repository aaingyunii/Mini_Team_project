package com.springboot.mini.data.repository;
// 리포지토리는 엔티티가 생성한 DB에 접근하는 데 사용되는 인터페이스

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.mini.data.entity.Webtoon;

public interface WebtoonRepository extends JpaRepository<Webtoon, String>{
    @Override
    List<Webtoon> findAll();
}
