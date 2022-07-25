package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search")
    public List<ItemDto> execSearch(@RequestParam String query) {
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJsonToItems(resultString);
    }
}
