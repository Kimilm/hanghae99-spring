package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.product.Product;
import com.sparta.selectshop.models.product.ProductMypriceRequestDto;
import com.sparta.selectshop.models.product.ProductRequestDto;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.models.user.UserRoleEnum;
import com.sparta.selectshop.repository.ApiUseTimeRepository;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import com.sparta.selectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    private final ApiUseTimeRepository apiUseTimeRepository;

    /**
     * (관리자용) 전체 상품 조회
     *
     * @param page   페이지 번호
     * @param size   한 페이지 아이템 수
     * @param sortBy 정렬 기준
     * @param isAsc  true: 오름차순 / false: 내림차순
     */
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/products")
    public Page<Product> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        page = page - 1;
        return productService.getAllProducts(page, size, sortBy, isAsc);
    }

    // 로그인한 회원이 등록한 관심 상품 조회
    @GetMapping("/api/products")
    public Page<Product> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        page = page - 1;
        return productService.getProducts(page, size, sortBy, isAsc, userId);
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Product product = productService.createProduct(requestDto, userId);
        return product;
    }


    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.update(id, requestDto);
    }

    // 상품에 폴더 추가
    @PostMapping("/api/products/{productId}/folder")
    public Long addFolder(
            @PathVariable Long productId,
            @RequestParam Long folderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        Product product = productService.addFolder(productId, folderId, user);

        return product.getId();
    }

}
