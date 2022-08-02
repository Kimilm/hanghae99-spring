package com.sparta.selectshop.service;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSearchService {

    private final NaverShopSearch naverShopSearch;

    public List<ItemDto> getItems(String query) {
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJsonToItems(resultString);
    }
}
