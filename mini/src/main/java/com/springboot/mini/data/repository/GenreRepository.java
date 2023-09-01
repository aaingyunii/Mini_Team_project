package com.springboot.mini.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.Genre;
import com.springboot.mini.data.dto.GenreRankDto;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, String>{
    @Query("SELECT new com.springboot.mini.data.dto.GenreRankDto(" +
            "t.genreRank, w.title, w.thumbnailUrl) " + 
            "FROM ToonRank t " +
            "inner JOIN Genre g ON t.genreCode = g.genreCode " +  
            "inner JOIN Webtoon w ON t.webtoonId = w.webtoonId " +
            "WHERE t.genreCode =:genreCode")
    List<GenreRankDto> queryByGenreCodeWithWebtoonWithToonRank(String genreCode);
}