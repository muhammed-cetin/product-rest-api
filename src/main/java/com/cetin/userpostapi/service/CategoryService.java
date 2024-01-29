package com.cetin.userpostapi.service;

import com.cetin.userpostapi.dto.CategoryDto;
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
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> saveCategory(CategoryDto categoryDto) {
        try {
            Category category = modelMapper.map(categoryDto, Category.class);
            categoryRepository.save(category);
            logger.info("Kategori başarıyla kaydedildi. Kategori Adı: {}", categoryDto.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Kategori kaydedilirken bir hata oluştu", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateCategory(Long categoryId, CategoryDto categoryDto) {
        try {
            if (categoryId != null) {
                Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

                if (optionalCategory.isPresent()) {
                    Category existingCategory = optionalCategory.get();
                    existingCategory.setName(categoryDto.getName());
                    categoryRepository.save(existingCategory);

                    logger.info("Kategori başarıyla güncellendi. Kategori ID: {}", categoryId);
                    return new ResponseEntity<>("Kategori başarıyla güncellendi", HttpStatus.NO_CONTENT);
                } else {
                    logger.warn("Kategori bulunamadı. Kategori ID: {}", categoryId);
                    return new ResponseEntity<>("Kategori bulunamadı", HttpStatus.NOT_FOUND);
                }
            } else {
                logger.warn("Kategori ID eksik");
                return new ResponseEntity<>("Kategori ID eksik", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Kategori güncellenirken bir hata oluştu", e);
            return new ResponseEntity<>("Kategori güncellenirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDto> categoryDtos = categories.stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Kategoriler getirilirken bir hata oluştu", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);

            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
                return new ResponseEntity<>(categoryDto, HttpStatus.OK);
            } else {
                logger.warn("Kategori bulunamadı. Kategori ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Kategori getirilirken bir hata oluştu", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteCategory(Long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);

            List<Product> optionalProduct = productRepository.findAllByCategoryId(id);

            if (!optionalProduct.isEmpty()) {
                return new ResponseEntity<>("Bu kategori silinemiyor çünkü ilişkili ürünleri var.", HttpStatus.BAD_REQUEST);
            }

            if (optionalCategory.isPresent()) {
                categoryRepository.delete(optionalCategory.get());
                logger.info("Kategori başarıyla silindi. Kategori ID: {}", id);
                return new ResponseEntity<>("Kategori başarıyla silindi", HttpStatus.NO_CONTENT);
            } else {
                logger.warn("Kategori bulunamadı. Kategori ID: {}", id);
                return new ResponseEntity<>("Kategori bulunamadı", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Kategori silinirken bir hata oluştu", e);
            return new ResponseEntity<>("Kategori silinirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
