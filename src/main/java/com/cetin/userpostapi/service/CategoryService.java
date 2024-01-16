package com.cetin.userpostapi.service;

import com.cetin.userpostapi.dto.CategoryDto;
import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.ICategoryRepository;
import com.cetin.userpostapi.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> saveCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (categoryId != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

            if (optionalCategory.isPresent()) {
                Category existingCategory = optionalCategory.get();
                existingCategory.setName(categoryDto.getName());
                categoryRepository.save(existingCategory);

                return new ResponseEntity<>("Category updated successfully", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Category ID is missing", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> new ResponseEntity<>(modelMapper.map(category, CategoryDto.class), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        List<Product> optionalProduct = productRepository.findAllByCategoryId(id);

        if (!optionalProduct.isEmpty()) {
            return new ResponseEntity<>("This category cannot be deleted because it has associated products.", HttpStatus.BAD_REQUEST);
        }

        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }

}
