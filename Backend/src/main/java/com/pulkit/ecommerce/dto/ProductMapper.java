package com.pulkit.ecommerce.dto;

import com.pulkit.ecommerce.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Productdto toDto(Product product){
        Productdto dto = new Productdto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        if(product.getCategory() != null){
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }
}
