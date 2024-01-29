package com.cetin.userpostapi.repository;

import com.cetin.userpostapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryId(Long categoryId);
    Product findByTitle(String title);
}
