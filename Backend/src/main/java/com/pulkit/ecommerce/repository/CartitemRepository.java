package com.pulkit.ecommerce.repository;

import com.pulkit.ecommerce.model.Cart;
import com.pulkit.ecommerce.model.CartItem;
import com.pulkit.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartitemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    Long Cart(Cart cart);
}
