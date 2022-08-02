package com.sparta.selectshop.service;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.product.ProductMypriceRequestDto;
import com.sparta.selectshop.models.product.ProductRequestDto;
import com.sparta.selectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int MIN_MY_PRICE = 100;

    private final ProductRepository productRepository;

    // (관리자) 전체 회원 관심상품 조회
    public Page<Product> getAllProducts(
            int page,
            int size,
            String sortBy,
            boolean isAsc
    ) {
        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        return productRepository.findAll(pageable);
    }

    // 회원 관심상품 조회
    public Page<Product> getProducts(
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            Long userId
    ) {
        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        return productRepository.findAllByUserId(userId, pageable);
    }

    // 페이징 객체 생성
    private Pageable createPageRequest(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    // 생성
    public Product createProduct(ProductRequestDto requestDto, Long userId) {
        return productRepository.save(new Product(requestDto, userId));
    }

    // 수정
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

    // 수정
    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }
}
