package com.cetin.userpostapi.service;

import com.cetin.userpostapi.dto.CategoryDto;
import com.cetin.userpostapi.dto.ProductDto;
import com.cetin.userpostapi.entity.Category;
import com.cetin.userpostapi.entity.Product;
import com.cetin.userpostapi.repository.ICategoryRepository;
import com.cetin.userpostapi.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<String> saveProduct(ProductDto productDto) {
        try {
            CategoryDto categoryDto = productDto.getCategory();

            if (categoryDto == null || categoryDto.getName() == null) {
                logger.error("Ürün kategorisi belirtilmemiş veya geçersiz.");
                return new ResponseEntity<>("Ürün kategorisi belirtilmemiş veya geçersiz.", HttpStatus.BAD_REQUEST);
            }

            String categoryName = categoryDto.getName();
            Category category = categoryRepository.findByName(categoryName).orElse(null);

            if (category == null) {
                logger.error("Belirtilen kategori bulunamadı. Kategori Adı: {}", categoryName);
                return new ResponseEntity<>("Belirtilen kategori bulunamadı. Kategori Adı: " + categoryName, HttpStatus.BAD_REQUEST);
            }

            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(category);

            Product existingProduct = productRepository.findByTitle(product.getTitle());

            if (existingProduct != null) {
                logger.error("Aynı başlığa sahip ürün zaten mevcut. Başlık: {}", product.getTitle());
                return new ResponseEntity<>("Aynı başlığa sahip ürün zaten mevcut. Başlık: " + product.getTitle(), HttpStatus.BAD_REQUEST);
            }

            productRepository.save(product);
            logger.info("Ürün başarıyla eklendi. Başlık: {}", product.getTitle());
            return new ResponseEntity<>("Ürün başarıyla eklendi. Başlık: " + product.getTitle(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Ürün eklenirken bir hata oluştu", e);
            return new ResponseEntity<>("Ürün eklenirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateProduct(String title, ProductDto updatedProductDto) {
        try {
            Product existingProduct = productRepository.findByTitle(title);

            if (existingProduct == null) {
                logger.error("Güncellenmek istenen ürün bulunamadı. Başlık: {}", title);
                return new ResponseEntity<>("Güncellenmek istenen ürün bulunamadı. Başlık: " + title, HttpStatus.NOT_FOUND);
            }

            String newPrice = updatedProductDto.getPrice();
            String newContent = updatedProductDto.getContent();
            CategoryDto newCategoryDto = updatedProductDto.getCategory();

            String categoryName = newCategoryDto != null ? newCategoryDto.getName() : null;

            if (categoryName != null) {
                Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);

                if (optionalCategory.isEmpty()) {
                    logger.error("Güncellenmek istenen ürün için belirtilen kategori bulunamadı. Kategori Adı: {}", categoryName);
                    return new ResponseEntity<>("Güncellenmek istenen ürün için belirtilen kategori bulunamadı. Kategori Adı: " + categoryName, HttpStatus.BAD_REQUEST);
                }

                existingProduct.setCategory(optionalCategory.get());
            }

            existingProduct.setPrice(newPrice);
            existingProduct.setContent(newContent);

            productRepository.save(existingProduct);
            logger.info("Ürün başarıyla güncellendi. Başlık: {}", title);
            return new ResponseEntity<>("Ürün başarıyla güncellendi. Başlık: " + title, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Ürün güncellenirken bir hata oluştu", e);
            return new ResponseEntity<>("Ürün güncellenirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ProductDto>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            List<ProductDto> productDtos = products.stream()
                    .map(product -> modelMapper.map(product, ProductDto.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Ürünler getirilirken bir hata oluştu", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ProductDto> getProductById(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            return optionalProduct.map(product -> new ResponseEntity<>(modelMapper.map(product, ProductDto.class), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Ürün getirilirken bir hata oluştu", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                productRepository.delete(optionalProduct.get());
                logger.info("Ürün başarıyla silindi. Ürün ID: {}", id);
                return new ResponseEntity<>("Ürün başarıyla silindi. Ürün ID: " + id, HttpStatus.NO_CONTENT);
            } else {
                logger.error("Silinecek ürün bulunamadı. Ürün ID: {}", id);
                return new ResponseEntity<>("Silinecek ürün bulunamadı. Ürün ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Ürün silinirken bir hata oluştu", e);
            return new ResponseEntity<>("Ürün silinirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
