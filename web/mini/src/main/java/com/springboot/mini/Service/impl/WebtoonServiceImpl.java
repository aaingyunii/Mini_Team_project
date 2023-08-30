package com.springboot.mini.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.repository.WebtoonRepository;

@Service
public class WebtoonServiceImpl implements WebtoonService{
    private WebtoonRepository webtoonRepository;

    @Autowired
    public WebtoonServiceImpl(WebtoonRepository webtoonRepository){
        this.webtoonRepository = webtoonRepository;
    }

    @Override
    public List<WebtoonDto> getWebtoonAll(){
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private WebtoonDto convertToDTO(Webtoon webtoon){
        WebtoonDto dto = new WebtoonDto();
        dto.setWebtoonId(webtoon.getWebtoonId());
        dto.setTitle(webtoon.getTitle());
        dto.setWriter(webtoon.getWriter());
        dto.setPainter(webtoon.getPainter());
        dto.setAge(webtoon.getAge());
        dto.setFavorite(webtoon.getFavorite());
        dto.setStarScore(webtoon.getStarScore());
        dto.setThumbnailUrl(webtoon.getThumbnailUrl());
        dto.setPublishDay(webtoon.getPublishDay());
        dto.setHashTag(webtoon.getHashTag());
        return dto;
    }
}
