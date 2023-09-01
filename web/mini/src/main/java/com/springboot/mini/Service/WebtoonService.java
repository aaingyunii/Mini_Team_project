package com.springboot.mini.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;

public interface WebtoonService {
    // 전체 조회
    Page<WebtoonDto> getWebtoonAll(int page, int pageSize);

    // 상세 조회
    List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId);

    // 검색
    List<WebtoonDto> searchWebtoonsByEvery(String every);

    // 랭킹검색
    String getGenreCode(String genre);
    String getGenreKorean(String genre);

    List<WebtoonGenreRankDto> getJoinDtos(String Code);
}
