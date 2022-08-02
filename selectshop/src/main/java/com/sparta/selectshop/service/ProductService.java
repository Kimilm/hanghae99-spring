package com.sparta.selectshop.service;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.product.ProductMypriceRequestDto;
import com.sparta.selectshop.models.product.ProductRequestDto;
import com.sparta.selectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int MIN_MY_PRICE = 100;

    private final ProductRepository productRepository;

    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId) {
        return productRepository.save(new Product(requestDto, userId));
    }

    @Transactional
    public Long update(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();

        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심가격, 최소: " + MIN_MY_PRICE);
        }

        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        product.update(requestDto);
        return id;
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
