package com.pulkit.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsdto {
    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
