package com.springboot.mini.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonRankDto;
import com.springboot.mini.data.dto.WeekDto;

import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.entity.WebtoonRank;
import com.springboot.mini.data.entity.Week;

import com.springboot.mini.data.repository.WebtoonRankRepository;
import com.springboot.mini.data.repository.WebtoonRepository;
import com.springboot.mini.data.repository.WebtoonSearchRepository;
import com.springboot.mini.data.repository.WeekRepository;


@Service
public class WebtoonServiceImpl implements WebtoonService{
    private WebtoonRepository webtoonRepository;
    private WebtoonSearchRepository webtoonSearchRepository;
    private WebtoonRankRepository webtoonRankRepository;
    private WeekRepository WeekRepository;

    @Autowired
    public WebtoonServiceImpl(WebtoonRepository webtoonRepository, WebtoonSearchRepository webtoonSearchRepository, WebtoonRankRepository webtoonRankRepository){
        this.webtoonRepository = webtoonRepository;
        this.webtoonSearchRepository = webtoonSearchRepository;
        this.webtoonRankRepository = webtoonRankRepository;
        this.WeekRepository = WeekRepository;

    }

    // 전체 조회
    @Override
    public List<WebtoonDto> getWebtoonAll(){
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 웹툰 상세 페이지
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

    @Override
    public  List<WebtoonRankDto> showRankByGenre(String genre){
        List<WebtoonRank> webtoonRanks = webtoonRankRepository.queryByGenre(genre);
        return webtoonRanks.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }
    // week
    @Override
    public List<WeekDto> getWebtoonsSortedByPublishDay() {
    // 요일별로 정렬된 웹툰 리스트 조회 기능을 구현합니다.
    List<Week> weeks = WeekRepository.findAllSortedByPublishDay();
    // 엔티티를 DTO로 변환하여 반환합니다.
        return weeks.stream().map(this::convertToDTO3).collect(Collectors.toList());
    }

    private WeekDto convertToDTO3(Week week){
        WeekDto dto = new WeekDto();
        dto.setWebtoonId(week.getWebtoonId());
        dto.setTitle(week.getTitle());
        dto.setPublishDay(week.getPublishDay());

//    // 나머지 필드들도 엔티티에서 가져와서 DTO에 설정
//    dto.setWriter(week.getWriter());
//    dto.setPainter(week.getPainter());
//    dto.setStarScore(week.getStarScore());
//    dto.setAge(week.getAge());
//    dto.setFavorite(week.getFavorite());
//    dto.setHashTag(week.getHashTag());
//    dto.setThumbnailUrl(week.getThumbnailUrl());
    return dto;
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

    private WebtoonRankDto convertToDTO2(WebtoonRank webtoonRank){
        WebtoonRankDto dto = new WebtoonRankDto();
        dto.setWebtoonId(webtoonRank.getWebtoonId());
        dto.setTitle(webtoonRank.getTitle());
        dto.setThumbnailUrl(webtoonRank.getThumbnailUrl());
        dto.setWebtoonRank(webtoonRank.getWebtoonRank());
        return dto;
    }
}
