package com.cetin.userpostapi.controller;

import com.cetin.userpostapi.dto.CategoryDto;
import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> fetchAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Optional<CategoryDto> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public void createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.saveCategory(categoryDto);
    }

    @PutMapping
    public void updateCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.saveCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
