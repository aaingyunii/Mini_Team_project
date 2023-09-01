package com.springboot.mini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mini.data.dto.GenreRankDto;
import com.springboot.mini.service.GenreService;
import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }

    @GetMapping("/webtoons/Rank/{code}")
    public ResponseEntity<List<GenreRankDto>> RankShow(@PathVariable String code){
        List<GenreRankDto> genreRankDtos = genreService.getJoinDtos(code);
        if (genreRankDtos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(genreRankDtos);
        }
    }
}
