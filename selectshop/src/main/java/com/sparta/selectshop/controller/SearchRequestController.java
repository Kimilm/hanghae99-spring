package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.ApiUseTime;
import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.repository.ApiUseTimeRepository;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchRequestController {

    private final ItemSearchService itemSearchService;

    private final ApiUseTimeRepository apiUseTimeRepository;

    @GetMapping("/api/search")
    public List<ItemDto> execSearch(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        long startTime = System.currentTimeMillis();

        try {
            return itemSearchService.getItems(query);
        } finally {
            long endTime = System.currentTimeMillis();

            long runTime = endTime - startTime;

            User loginUser = userDetails.getUser();

            // API 사용 시간 DB 기록
            ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser).orElse(null);

            if (apiUseTime == null) {
                apiUseTime = new ApiUseTime(loginUser, runTime);
            } else {
                apiUseTime.addUseTime(runTime);
            }

            log.info("[API Use Time] username: " + loginUser.getUsername());

            apiUseTimeRepository.save(apiUseTime);
        }
    }
}
