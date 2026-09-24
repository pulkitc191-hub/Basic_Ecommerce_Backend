package com.pulkit.ecommerce.service;

import com.pulkit.ecommerce.dto.CartMapper;
import com.pulkit.ecommerce.dto.Cartdto;
import com.pulkit.ecommerce.exception.ResourceNotFound;
import com.pulkit.ecommerce.model.Cart;
import com.pulkit.ecommerce.model.CartItem;
import com.pulkit.ecommerce.model.Product;
import com.pulkit.ecommerce.repository.CartRepository;
import com.pulkit.ecommerce.repository.CartitemRepository;
import com.pulkit.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartitemRepository cartitemRepository;
    private final ProductRepository ProductRepository;
    private final CartMapper cartMapper;


    public CartService(CartRepository cartRepository, CartitemRepository cartitemRepository, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartitemRepository = cartitemRepository;
        this.ProductRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    public Cartdto addProductToCart(Long Cartid, Long Productid, Integer quantity){
        Cart cart = cartRepository.findById(Cartid).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = ProductRepository.findById(Productid).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartitemRepository.findByCartAndProduct(cart, product).orElse(null);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {

            cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }
        cartitemRepository.save(cartItem);
        return cartMapper.todto(cart);
    }

    public Cartdto createCart(){
        Cart cart = new Cart();
        cart = cartRepository.save(cart);

        return cartMapper.todto(cart);
    }

    @Transactional(readOnly = true)
    public Cartdto getCart(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartMapper.todto(cart);
    }

    public Cartdto removeItemFromCart(Long cartId, Long cartItemId){

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cartitemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if(!cartItem.getCart().getId().equals(cart.getId())){
            throw new ResourceNotFound("Cart item does not belong to this cart");
        }
        cartitemRepository.delete(cartItem);
    return cartMapper.todto(cart);
    }

    public Cartdto updateCartItemQuantity(Long cartId, Long cartItemId, Integer quantity){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cartitemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));

        if(!cartItem.getCart().getId().equals(cart.getId())){
            throw new ResourceNotFound("Cart item does not belong to this cart");
        }

        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        cartItem.setQuantity(quantity);
        cartitemRepository.save(cartItem);

        return cartMapper.todto(cart);
    }

}
