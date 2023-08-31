package com.springboot.mini.Service;

import java.util.List;

import com.springboot.mini.data.dto.WebtoonDto;

public interface WebtoonService {
    List<WebtoonDto> getWebtoonAll();

    List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId);

    List<WebtoonDto> searchWebtoonsByEvery(String every);

}
