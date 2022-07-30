package com.sparta.selectshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.selectshop.models.user.SignupRequestDto;
import com.sparta.selectshop.service.KakaoUserService;
import com.sparta.selectshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    // 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 카카오 인가코드 처리
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/selectshop";
    }

    // 회원가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }
}
