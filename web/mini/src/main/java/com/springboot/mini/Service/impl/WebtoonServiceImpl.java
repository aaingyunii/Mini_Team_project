package com.springboot.mini.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.repository.WebtoonRepository;
import com.springboot.mini.data.repository.WebtoonSearchRepository;

@Service
public class WebtoonServiceImpl implements WebtoonService{
    private WebtoonRepository webtoonRepository;

    private WebtoonSearchRepository webtoonSearchRepository;

    @Autowired
    public WebtoonServiceImpl(WebtoonRepository webtoonRepository, WebtoonSearchRepository webtoonSearchRepository){
        this.webtoonRepository = webtoonRepository;
        this.webtoonSearchRepository = webtoonSearchRepository;
    }

    @Override
    public List<WebtoonDto> getWebtoonAll(){
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId){
        List<Webtoon> webtoons = webtoonRepository.queryByWebtoonId(webtoonId);
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 검색
    @Override
    public List<WebtoonDto> searchWebtoonsByEvery(String every) {
        List<Webtoon> webtoons = webtoonSearchRepository.queryByEvery(every);
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
