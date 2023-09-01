package com.springboot.mini.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.ToonRank;

import java.util.List;

public interface ToonRepository extends JpaRepository<ToonRank, String>  {
    @Query("Select t From ToonRank t where genre_code = :genre_code")
    List<ToonRank> queryByGenre(String genre_code);
}
