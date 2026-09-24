package com.pulkit.ecommerce.dto;

import com.pulkit.ecommerce.model.Cart;
import com.pulkit.ecommerce.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {
    public Cartdto todto(Cart cart){
        Cartdto cartdto = new Cartdto();
        cartdto.setId(cart.getId());
        List<CartItemdto> items = cart.getCartItem().stream().map(this :: toCartItemdto).toList();
        cartdto.setItems(items);

        Double totalPrice = cart.getCartItem().stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        cartdto.setTotalPrice(totalPrice);
        return cartdto;
    }

    private CartItemdto toCartItemdto(CartItem cartItem){
        CartItemdto itemdto = new CartItemdto();

        itemdto.setId(cartItem.getId());
        itemdto.setQuantity(cartItem.getQuantity());

        if(cartItem.getProduct() != null){
            itemdto.setProductId(cartItem.getProduct().getId());
            itemdto.setProductname(cartItem.getProduct().getName());
            itemdto.setPrice(cartItem.getProduct().getPrice());
        }
        return itemdto;
    }
}
