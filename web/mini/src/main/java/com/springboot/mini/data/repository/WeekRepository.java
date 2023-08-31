package com.springboot.mini.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.Week;

public interface WeekRepository extends JpaRepository<Week, Long> {
    @Query("SELECT w FROM Week w ORDER BY w.publishDay")
    List<Week> findAllSortedByPublishDay();
}

