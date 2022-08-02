package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.service.ItemSearchService;
import com.sparta.selectshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchRequestController {

    private final ItemSearchService itemSearchService;

    @GetMapping("/api/search")
    public List<ItemDto> execSearch(@RequestParam String query) {
        return itemSearchService.getItems(query);
    }
}
