package com.pulkit.ecommerce.controller;

import com.pulkit.ecommerce.dto.Cartdto;
import com.pulkit.ecommerce.model.Cart;
import com.pulkit.ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cartdto createCart(){
        return cartService.createCart();
    }

    @PostMapping("/{cartId}/items")
    public Cartdto addproductToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam Integer quantity){
        return cartService.addProductToCart(cartId, productId, quantity);
    }

    @GetMapping("/{cartId}")
    public Cartdto getCart(@PathVariable Long cartId){
        return cartService.getCart(cartId);
    }

    public Cartdto removeItemFromCart(@PathVariable("cartId") Long cartId, @PathVariable("cartItemId") Long cartItemId){

    return cartService.removeItemFromCart(cartId, cartItemId);
    }

}
