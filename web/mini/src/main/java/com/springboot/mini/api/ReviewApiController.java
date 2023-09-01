package com.springboot.mini.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.springboot.mini.Service.impl.ReviewServiceImpl;
import com.springboot.mini.data.dto.ReviewForm;
import com.springboot.mini.data.entity.Review;

@RestController
public class ReviewApiController {
    @Autowired
    private ReviewServiceImpl reviewService;

    // // 전체 리뷰 게시판
    // @GetMapping("/api/reviews")
    // public List<Review> index() {
    //     return reviewService.index();
    // }

    // // 리뷰 열람 - id
    // @GetMapping("/api/reviews/{id}")
    // public Review show(@PathVariable Long id) {
    //     return reviewService.show(id);
    // }

    // // 신규 리뷰 작성
    // @PostMapping("/api/reviews")
    // public ResponseEntity<Review> create(@RequestBody ReviewForm dto) {
    //     Review created = reviewService.create(dto);
    //     return (created != null)?
    //         ResponseEntity.status(HttpStatus.OK).body(created):
    //         ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    // }

    // // 기존 리뷰 수정
    // @PatchMapping("/api/reviews/{id}")
    // public ResponseEntity<Review> update(@PathVariable Long id,
    //                                     @RequestBody ReviewForm dto) {
    //     Review updated = reviewService.update(id, dto);
    //     return (updated != null) ?
    //     ResponseEntity.status(HttpStatus.OK).body(updated):
    //     ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    // }

    // // 리뷰 삭제
    // @DeleteMapping("/api/reviews/{id}")
    // public ResponseEntity<Review> delete(@PathVariable Long id) {
    //     Review deleted = reviewService.delete(id);
    //     return (deleted != null)?
    //         ResponseEntity.status(HttpStatus.NO_CONTENT).build():
    //         ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    // }

    // @PostMapping("/api/transaction-test")
    // public ResponseEntity<List<Review>> transactionTest(@RequestBody List<ReviewForm> dtos) {
    //     List<Review> createdList = reviewService.createArticles(dtos);
    //     return (createdList != null) ?
    //         ResponseEntity.status(HttpStatus.NO_CONTENT).body(createdList):
    //         ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    // }

}
