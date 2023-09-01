package com.springboot.mini.service;

import com.springboot.mini.data.dto.GenreRankDto;

import java.util.List;

public interface GenreService {
    List<GenreRankDto> getJoinDtos(String Code);
}
