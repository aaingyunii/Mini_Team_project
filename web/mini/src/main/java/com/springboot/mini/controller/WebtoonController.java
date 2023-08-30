package com.springboot.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.repository.WebtoonRepository;


@Controller
public class WebtoonController {
    @Autowired
    private WebtoonRepository webtoonRepository;

    @GetMapping("/webtoons")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        List<Webtoon> webtoonEntityList = (List<Webtoon>) webtoonRepository.findAll();

        // 2. 모델에 데이터 등록
        model.addAttribute("webtoonList", webtoonEntityList);

        // 3. 뷰에 전송
        return "webtoons/index";
    }

}
