package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.repository.ApiUseTimeRepository;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchRequestController {

    private final ItemSearchService itemSearchService;

    private final ApiUseTimeRepository apiUseTimeRepository;

    @GetMapping("/api/search")
    public List<ItemDto> execSearch(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return itemSearchService.getItems(query);
    }
}
