package com.cetin.userpostapi.controller;

import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> fetchAll() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public void createCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
