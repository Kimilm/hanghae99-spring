package com.sparta.selectshop.utils;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.repository.ProductRepository;
import com.sparta.selectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;

    // 초, 분, 시, 일, 월, 주
    // 매일 새벽 1시에 수행
    // * * 1 * * * -> 1시 0분 0초부터 1시 59분 59초까지 매 초 실행됨 주의
    @Scheduled(cron = "0 0 1 * * *")
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");

        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            // 네이버 제한, 1초에 상품 하나씩 조회
            TimeUnit.SECONDS.sleep(1);

            String title = product.getTitle();
            String resultString = naverShopSearch.search(title);
            List<ItemDto> itemDtoList = naverShopSearch.fromJsonToItems(resultString);

            Long id = product.getId();
            ItemDto itemDto = itemDtoList.get(0);
            productService.updateBySearch(id, itemDto);
        }
    }
}
