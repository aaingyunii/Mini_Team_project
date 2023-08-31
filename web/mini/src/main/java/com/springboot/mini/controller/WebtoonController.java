package com.springboot.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.ReviewForm;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.entity.Review;
import com.springboot.mini.data.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@Controller
public class WebtoonController {
    private final WebtoonService webtoonService;

    @Autowired
    public WebtoonController(WebtoonService webtoonService){
        this.webtoonService = webtoonService;
    }

    @GetMapping("/webtoons")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonAll();

        // 2. 모델에 데이터 등록
        model.addAttribute("webtoonList", webtoonDtos);

        // 3. 뷰에 전송
        return "webtoons/index";
    }

}
