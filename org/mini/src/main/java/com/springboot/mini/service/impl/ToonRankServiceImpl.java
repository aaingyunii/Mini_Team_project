package com.springboot.mini.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mini.data.dto.ToonRankDto;
import com.springboot.mini.data.entity.ToonRank;
import com.springboot.mini.data.repository.ToonRepository;
import com.springboot.mini.service.ToonRankService;

@Service
public class ToonRankServiceImpl implements ToonRankService { 
    private ToonRepository toonRepository;

    @Autowired
    public ToonRankServiceImpl(ToonRepository toonRepository){
        this.toonRepository = toonRepository;
    }

    @Autowired
    public List<ToonRankDto> getToonRankByGenre(String Genre_Code){
        List<ToonRank> toonRanks = toonRepository.queryByGenre(Genre_Code);
        return toonRanks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ToonRankDto convertToDTO(ToonRank toonRank){
        ToonRankDto dto = new ToonRankDto();
        dto.setGenre(toonRank.getGenre());
        dto.setWebtoon(toonRank.getWebtoon());
        dto.setGenre_rank(toonRank.getGenre_rank());
        return dto;
    }
}
