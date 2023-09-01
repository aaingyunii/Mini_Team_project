package com.springboot.mini.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webtoon")
public class WebtoonController {

    @GetMapping("/Rank/{GenreId}")
    public String WebtoonRankingByGenre(@PathVariable String GenreId){
        return GenreId;
    }

}
