package com.springboot.mini.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.WebtoonGenre;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;

import java.util.List;

public interface WebtoonGenreRepository extends JpaRepository<WebtoonGenre, String>{
    @Query("Select g.genreCode FROM WebtoonGenre g where g.labelEnglish = :genre")
    String queryByGenreCode(String genre);

    @Query("Select g.labelKorea FROM WebtoonGenre g where g.labelEnglish = :genre")
    String queryByGenreKoran(String genre);

    @Query("SELECT new com.springboot.mini.data.dto.WebtoonGenreRankDto(" +
            "w.webtoonId, w.title, w.thumbnailUrl, t.genreRank) " + 
            "FROM WebtoonRank t " +
            "inner JOIN WebtoonGenre g ON t.genreCode = g.genreCode " +  
            "inner JOIN Webtoon w ON t.webtoonId = w.webtoonId " +
            "WHERE t.genreCode =:genreCode")
    List<WebtoonGenreRankDto> queryByGenreCodeWithWebtoonWithWebtoonRank(String genreCode);
}