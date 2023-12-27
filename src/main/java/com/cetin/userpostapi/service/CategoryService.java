package com.cetin.userpostapi.service;

import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.ICategoryRepository;
import com.cetin.userpostapi.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;


    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        List<Product> optionalProduct = productRepository.findAllByCategoryId(id);

        if (!optionalProduct.isEmpty()){
            throw new RuntimeException("This category can not delete");
        }
        categoryRepository.delete(optionalCategory.get());

    }
}
