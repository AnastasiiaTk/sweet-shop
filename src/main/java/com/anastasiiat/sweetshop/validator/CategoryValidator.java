package com.anastasiiat.sweetshop.validator;

import com.anastasiiat.sweetshop.service.ProductService;
import com.anastasiiat.sweetshop.util.IterableUtils;
import com.anastasiiat.sweetshop.error.InvalidCategoryOperationException;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private Environment environment;

    public void validateToDelete(Integer categoryId) throws InvalidCategoryOperationException {
        Iterable<Category> subcategories = categoryService.findSubcategories(categoryId);
        if (IterableUtils.isNotEmpty(subcategories)) {
            throw new InvalidCategoryOperationException(environment.getProperty("category.has.subcategories.error"));
        }
        Iterable<Product> products = productService.findByСategoryId(categoryId);
        if (IterableUtils.isNotEmpty(products)) {
            throw new InvalidCategoryOperationException(environment.getProperty("category.has.products.error"));
        }
    }
}
