package com.pulkit.ecommerce.service;

import com.pulkit.ecommerce.exception.ResourceNotFound;
import com.pulkit.ecommerce.model.Category;
import com.pulkit.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategorybyId(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));
    }

    public Category updateCategory(Long id, Category category){
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public Category deleteCategory(Long id){
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));
        categoryRepository.delete(existingCategory);
        return existingCategory;
    }
}
