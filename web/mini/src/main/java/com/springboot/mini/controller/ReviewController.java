package com.springboot.mini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.mini.data.dto.ReviewForm;
import com.springboot.mini.data.entity.Review;
import com.springboot.mini.data.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewController {
    

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(value="/reviews/new")
    public String newArticleForm() {
            return "reviews/new";
    }

    @PostMapping("/reviews/create")
    public String createReview(ReviewForm form) {
        log.info(form.toString());
        Review review = form.toEntity();
        Review saved = reviewRepository.save(review);
        log.info(saved.toString());
        return "redirect:/reviews/" + saved.getId();
    }
    
    @GetMapping("/reviews/{id}")
    public String show(@PathVariable Long id, Model model) {
        //  터미널 상에 로그로 리뷰 id 번호 출력
        log.info("id = " + id);

        // id를 이용해 데이터 호출
        Review reviewEntity = reviewRepository.findById(id).orElse(null);
        System.out.println(reviewEntity);
        // id가 없을 경우 null을 리턴하고, 그 외의 경우 data를 id 값을 이용해 reviewEntity 변수에 리턴함.

        // 모델에 데이터 추가
        model.addAttribute("reviews", reviewEntity);
        return "reviews/show";
    }
}
