package com.pulkit.ecommerce.controller;

import com.pulkit.ecommerce.dto.Productdto;
import com.pulkit.ecommerce.model.Product;
import com.pulkit.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Productdto> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public Productdto getProductById(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @PostMapping
    public Productdto addProduct(@Valid @RequestBody Productdto productdto){
        return productService.addProduct(productdto);
    }

    @PutMapping("/{id}")
    public Productdto updateProduct(@PathVariable Long id, @Valid @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Productdto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
