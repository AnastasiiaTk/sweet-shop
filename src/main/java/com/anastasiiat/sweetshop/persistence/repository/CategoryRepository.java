package com.anastasiiat.sweetshop.persistence.repository;

import com.anastasiiat.sweetshop.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("FROM Category c ORDER BY COALESCE(c.parentCategory.id, c.id), c.parentCategory.id, c.name")
    Iterable<Category> findAllOrderByTree();

    Iterable<Category> findAllByParentCategoryIsNullOrderByName();

    Iterable<Category> findAllByParentCategoryCategoryId(Integer categoryId);
}
