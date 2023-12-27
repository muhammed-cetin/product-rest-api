package com.cetin.userpostapi.service;

import com.cetin.userpostapi.dto.CategoryDto;
import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.ICategoryRepository;
import com.cetin.userpostapi.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;


    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public void saveCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public Optional<CategoryDto> getCategoryById(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory
                .map(category -> modelMapper.map(category,CategoryDto.class));
    }

    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        List<Product> optionalProduct = productRepository.findAllByCategoryId(id);

        if (!optionalProduct.isEmpty()) {
            throw new RuntimeException("This category can not delete");
        }
        categoryRepository.delete(optionalCategory.get());

    }
}
