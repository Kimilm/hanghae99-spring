package com.sparta.selectshop.repository;

import com.sparta.selectshop.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
