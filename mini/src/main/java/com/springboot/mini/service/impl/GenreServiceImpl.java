package com.springboot.mini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mini.data.dto.GenreRankDto;
import com.springboot.mini.data.repository.GenreRepository;
import com.springboot.mini.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreRankDto> getJoinDtos(String Code){
        List<GenreRankDto> genreRanks = genreRepository.queryByGenreCodeWithWebtoonWithToonRank(Code);
        return genreRanks;
    }
}
