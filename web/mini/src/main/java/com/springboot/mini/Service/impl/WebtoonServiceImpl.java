package com.springboot.mini.Service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;
import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.repository.WebtoonGenreRepository;
import com.springboot.mini.data.repository.WebtoonRepository;
import com.springboot.mini.data.repository.WebtoonSearchRepository;

@Service
public class WebtoonServiceImpl implements WebtoonService{
    
    private WebtoonRepository webtoonRepository;
    private WebtoonSearchRepository webtoonSearchRepository;
    private WebtoonGenreRepository webtoonGenreRepository;

    @Autowired
    public WebtoonServiceImpl(WebtoonRepository webtoonRepository, WebtoonSearchRepository webtoonSearchRepository
    , WebtoonGenreRepository webtoonGenreRepository){
        this.webtoonRepository = webtoonRepository;
        this.webtoonSearchRepository = webtoonSearchRepository;
        this.webtoonGenreRepository = webtoonGenreRepository;

    }

    // 전체 조회
    @Override
    public Page<WebtoonDto> getWebtoonAll(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Webtoon> webtoonPage = webtoonRepository.findAll(pageable);

        // List<Webtoon> webtoons = webtoonRepository.findAll();
        // return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
        return webtoonPage.map(this::convertToDTO);
    }


    // 웹툰 상세 페이지
    @Override
    public List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId){
        List<Webtoon> webtoons = webtoonRepository.queryByWebtoonId(webtoonId);
        return webtoons.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }

    // 검색
    @Override
    public List<WebtoonDto> searchWebtoonsByEvery(String every) {
        List<Webtoon> webtoons = webtoonSearchRepository.queryByEvery(every);
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    @Override
    public String getGenreCode(String genre) {
        String genreCode = webtoonGenreRepository.queryByGenreCode(genre);
        return genreCode;
    }

    @Override
    public String getGenreKorean(String genre) {
        String genreKorean = webtoonGenreRepository.queryByGenreKoran(genre);
        return genreKorean;
    }

    // 장르별 TOP10 랭크
    @Override
    public List<WebtoonGenreRankDto> getJoinDtos(String Code){
        List<WebtoonGenreRankDto> genreRanks = webtoonGenreRepository.queryByGenreCodeWithWebtoonWithWebtoonRank(Code);
        return genreRanks;
    }

    private WebtoonDto convertToDTO(Webtoon webtoon){
        WebtoonDto dto = new WebtoonDto();
        dto.setWebtoonId(webtoon.getWebtoonId());
        dto.setTitle(webtoon.getTitle());
        dto.setThumbnailUrl(webtoon.getThumbnailUrl());
        dto.setHashTag(webtoon.getHashTag());
        return dto;
    }

    private WebtoonDto convertToDTO2(Webtoon webtoon){
        WebtoonDto dto = new WebtoonDto();
        dto.setWebtoonId(webtoon.getWebtoonId());
        dto.setTitle(webtoon.getTitle());
        dto.setWriter(webtoon.getWriter());
        dto.setPainter(webtoon.getPainter());
        dto.setAge(webtoon.getAge());
        dto.setFavorite(webtoon.getFavorite().toString());
        dto.setStarScore(webtoon.getStarScore());
        dto.setThumbnailUrl(webtoon.getThumbnailUrl());
        dto.setPublishDay(webtoon.getPublishDay());
        dto.setHashTag(webtoon.getHashTag());

        dto = changeWords(dto);

        return dto;
    }

    private WebtoonDto changeWords(WebtoonDto webtoonDto){
        String age = webtoonDto.getAge();
        String publishDay = webtoonDto.getPublishDay();
        String favor = webtoonDto.getFavorite();
        
        // HashMap을 사용하여 요일을 번역하는 맵을 생성합니다.
        Map<String, String> weekDay = new HashMap<>();

        // 요일을 맵에 추가합니다.
        weekDay.put("MONDAY", "월요일");
        weekDay.put("TUESDAY", "화요일");
        weekDay.put("WEDNESDAY", "수요일");
        weekDay.put("THURSDAY", "목요일");
        weekDay.put("FRIDAY", "금요일");
        weekDay.put("SATURDAY", "토요일");
        weekDay.put("SUNDAY", "일요일");
        weekDay.put("Finish", "연재 종료");

        // 시청 연령 제한 표시 이름 바꾸기
        if("RATE_15".equals(age)){
            webtoonDto.setAge("15세 이용가");
        }else if("RATE_18".equals(age)){
            webtoonDto.setAge("18세 이용가, 성인물");
        }else if("RATE_12".equals(age)){
            webtoonDto.setAge("12세 이용가");
        }else{
            webtoonDto.setAge("전체 이용가");
        }
        
        // 연재 요일을 한글로 바꾸기
        if(publishDay.length()>9){
            String[] dayArray = publishDay.split(",");

            StringBuilder translatedDay = new StringBuilder();

            for (String day : dayArray) {
                String translated = weekDay.get(day);
                if (translated != null) {
                    translatedDay.append(translated);
                    translatedDay.append(",");
                }
            }

            // 마지막 쉼표 제거
            if (translatedDay.length() > 0) {
                translatedDay.setLength(translatedDay.length() - 1);
            }

            webtoonDto.setPublishDay(translatedDay.toString());
        }else{
            String translatedDay = weekDay.get(publishDay);
            webtoonDto.setPublishDay(translatedDay.toString());
        }

        // 관심도 수를 자릿수마다 ',' 로 형식화
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        try{
            Number parsedNumber = decimalFormat.parse(favor);
            String formattedNum = decimalFormat.format(parsedNumber);
            webtoonDto.setFavorite(formattedNum);

        }catch(ParseException pe){
            pe.printStackTrace();
        }
        
        return webtoonDto;
    }

}


