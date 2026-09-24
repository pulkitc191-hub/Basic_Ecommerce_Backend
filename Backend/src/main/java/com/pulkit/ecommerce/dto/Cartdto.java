package com.pulkit.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cartdto {
    private Long id;
    private List<CartItemdto> items;
    private Double totalPrice;
}
