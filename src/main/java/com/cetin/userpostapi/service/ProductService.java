package com.cetin.userpostapi.service;

import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final IProductRepository productRepository;


    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        Optional<Product> optionalProduct =productRepository.findById(id);
        productRepository.delete(optionalProduct.get());
    }


}