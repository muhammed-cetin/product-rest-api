package com.cetin.userpostapi.controller;

import com.cetin.userpostapi.dto.ProductDto;
import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> fetchAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<ProductDto> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
