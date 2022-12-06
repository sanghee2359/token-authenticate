package com.token.authenticate.controller;

import com.token.authenticate.domain.dto.UserLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @PostMapping("")
    public ResponseEntity<String> writeReview(@RequestBody UserLoginRequest dto) {
        return ResponseEntity.ok().body(dto.getUserName()+"님의 리뷰 등록이 완료되었습니다.");
    }
}
