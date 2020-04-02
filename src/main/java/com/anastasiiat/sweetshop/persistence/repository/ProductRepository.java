package com.anastasiiat.sweetshop.persistence.repository;

import com.anastasiiat.sweetshop.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Iterable<Product> findAllByOrderByName();

    @Query("FROM Product p WHERE p.category.categoryId = :categoryId " +
            "or p.category.parentCategory.categoryId = :categoryId ORDER BY p.name")
    Iterable<Product> findAllByCategoryId(@Param("categoryId") Integer categoryId);
}
