package com.cetin.userpostapi.controller;

import com.cetin.userpostapi.dto.ProductDto;
import com.cetin.userpostapi.service.ProductService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductDto>> fetchAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @PutMapping("/{title}")
    public ResponseEntity<String> updateProduct(@PathVariable String title, @RequestBody ProductDto updatedProductDto) {
        return productService.updateProduct(title, updatedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
