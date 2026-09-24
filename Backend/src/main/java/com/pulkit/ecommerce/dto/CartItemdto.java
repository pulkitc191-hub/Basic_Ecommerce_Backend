package com.pulkit.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemdto {
    private Long id;

    private Long productId;

    private String productname;

    private Integer quantity;

    private Double price;
}
