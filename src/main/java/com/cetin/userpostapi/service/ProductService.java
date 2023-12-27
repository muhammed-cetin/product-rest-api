package com.cetin.userpostapi.service;

import com.cetin.userpostapi.dto.ProductDto;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;


    public ProductService(IProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public void saveProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct
                .map(product -> modelMapper.map(product,ProductDto.class));
    }

    public void deleteProduct(Long id){
        Optional<Product> optionalProduct =productRepository.findById(id);
        productRepository.delete(optionalProduct.get());
    }


}