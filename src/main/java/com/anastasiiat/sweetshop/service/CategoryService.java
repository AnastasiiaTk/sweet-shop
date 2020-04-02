package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.persistence.repository.CategoryRepository;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAllOrderByTree();
    }

    public Iterable<Category> getParentCategories() {
        return categoryRepository.findAllByParentCategoryIsNullOrderByName();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElse(null);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Iterable<Category> findSubcategories(Integer categoryId) {
        return categoryRepository.findAllByParentCategoryCategoryId(categoryId);
    }

}
