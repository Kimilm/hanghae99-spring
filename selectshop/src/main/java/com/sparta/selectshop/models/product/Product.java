package com.sparta.selectshop.models.product;

import com.sparta.selectshop.models.item.ItemDto;
import com.sparta.selectshop.repository.ProductRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    public Product(ProductRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.image = requestDto.getImage();
        this.myprice = 0;
    }

    public void update(ProductMypriceRequestDto requestDto) {
        myprice = requestDto.getMyprice();
    }

    public void updateByItemDto(ItemDto itemDto) {
        this.lprice = itemDto.getLprice();
    }
}
