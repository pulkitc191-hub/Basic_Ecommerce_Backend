package com.pulkit.ecommerce.service;

import com.pulkit.ecommerce.dto.ProductMapper;
import com.pulkit.ecommerce.dto.Productdto;
import com.pulkit.ecommerce.exception.ResourceNotFound;
import com.pulkit.ecommerce.model.Category;
import com.pulkit.ecommerce.model.Product;
import com.pulkit.ecommerce.repository.CategoryRepository;
import com.pulkit.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public Page<Productdto> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public Productdto addProduct(Productdto productdto){
        Product product = new Product();
        product.setName(productdto.getName());
        product.setPrice(productdto.getPrice());
        product.setQuantity(productdto.getQuantity());
        product.setDescription(productdto.getDescription());

        if(productdto.getCategoryId() != null){
            Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow(() -> new RuntimeException("Cannot find category"));
            product.setCategory(category);
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public Productdto getProduct(Long id){
        return productMapper.toDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found")));
    }


    public Productdto updateProduct(Long id, Product product){
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        if(product.getCategory() != null){
            Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow(() -> new ResourceNotFound("Category not found"));
            existingProduct.setCategory(category);
        }
        return productMapper.toDto(existingProduct);
    }

    public Productdto deleteProduct(Long id){
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found"));
        productRepository.delete(existingProduct);
        return productMapper.toDto(existingProduct);
    }
}
