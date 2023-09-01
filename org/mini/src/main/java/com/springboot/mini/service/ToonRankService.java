package com.springboot.mini.service;

import com.springboot.mini.data.dto.ToonRankDto;
import java.util.List;

public interface ToonRankService {
    List<ToonRankDto> getToonRankByGenre(String Genre_Code);
}
