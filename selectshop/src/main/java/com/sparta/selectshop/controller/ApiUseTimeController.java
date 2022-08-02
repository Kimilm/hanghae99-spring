package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.ApiUseTime;
import com.sparta.selectshop.models.user.UserRoleEnum;
import com.sparta.selectshop.repository.ApiUseTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiUseTimeController {

    private final ApiUseTimeRepository apiUseTimeRepository;

    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/use/time")
    public List<ApiUseTime> getAllApiUseTime() {
        return apiUseTimeRepository.findAll();
    }
}
