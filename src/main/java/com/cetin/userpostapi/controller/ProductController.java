package com.cetin.userpostapi.controller;

import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.service.ProductService;
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
    public List<Product> fetchAll() {
        return productService.getAllProducts();
    }

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
