package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceTest extends SweetShopApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void getRootCategoriesTest() {
        Iterable<Category> allCategories = categoryService.getAllCategories();
        assertNotNull(allCategories);
        assertEquals(10, IterableUtil.sizeOf(allCategories));
        List<Category> categoryList = Lists.newArrayList(allCategories);
        assertEquals("Marshmallow", categoryList.get(0).getName());
        assertEquals("Apple Marshmallows", categoryList.get(1).getName());
        assertEquals("Chocolate Dipped Marshmallows", categoryList.get(2).getName());
        assertEquals("Chocolate", categoryList.get(3).getName());
        assertEquals("Dark Chocolate", categoryList.get(4).getName());
        assertEquals("Milk Chocolate", categoryList.get(5).getName());
        assertEquals("White Chocolate", categoryList.get(6).getName());
        assertEquals("Cake", categoryList.get(7).getName());
        assertEquals("Chocolate Cake", categoryList.get(8).getName());
        assertEquals("Low Fat Cake", categoryList.get(9).getName());
    }

    @Test
    public void getParentCategoiesTest() {
        Iterable<Category> categories = categoryService.getParentCategories();
        assertNotNull(categories);
        assertEquals(3, IterableUtil.sizeOf(categories));
        List<Category> categoryList = Lists.newArrayList(categories);
        assertNull(categoryList.get(0).getParentCategory());
        assertNull(categoryList.get(1).getParentCategory());
        assertNull(categoryList.get(2).getParentCategory());
        assertEquals("Cake", categoryList.get(0).getName());
        assertEquals("Chocolate", categoryList.get(1).getName());
        assertEquals("Marshmallow", categoryList.get(2).getName());
    }


    @Test
    public void saveParentCategoryTest() {
        saveParentCategory();
        Iterable<Category> allCategories = categoryService.getAllCategories();
        assertTrue(IterableUtil.sizeOf(allCategories) > 10);
        List<Category> categoryList = Lists.newArrayList(allCategories);
        assertEquals("Parent Category", categoryList.get(10).getName());
        assertNull(categoryList.get(10).getParentCategory());
    }

    @Test
    public void saveCategoryTest() {
        Category parentCategory = saveParentCategory();
        Category category = new Category();
        category.setName("New Category");
        category.setParentCategory(parentCategory);
        categoryService.saveCategory(category);
        Iterable<Category> allCategories = categoryService.getAllCategories();
        assertEquals(12, IterableUtil.sizeOf(allCategories));
        List<Category> categoryList = Lists.newArrayList(allCategories);
        assertEquals("New Category", categoryList.get(11).getName());
        assertEquals("Parent Category", categoryList.get(11).getParentCategory().getName());
    }

    @Test
    public void updateCategoryTest() {
        List<Category> parents = Lists.newArrayList(categoryService.getParentCategories());
        Category category = saveParentCategory();
        category.setName("Updated parent category");
        category.setParentCategory(parents.get(0));
        categoryService.saveCategory(category);
        Iterable<Category> updatedCategories = categoryService.getAllCategories();
        assertEquals(11, IterableUtil.sizeOf(updatedCategories));
        Category resultCategory = categoryService.findCategoryById(category.getCategoryId());
        assertEquals("Updated parent category", resultCategory.getName());
        assertEquals("Cake", resultCategory.getParentCategory().getName());
    }

    @Test
    public void findCategoryByIdTest() {
        Category category = categoryService.findCategoryById(1);
        assertNotNull(category);
        assertEquals(Integer.valueOf(1), category.getCategoryId());
        assertEquals("Marshmallow", category.getName());
        assertNull(category.getParentCategory());
    }

    @Test
    public void deleteCategoryTest() {
        Category category = new Category();
        category.setParentCategoryId(1);
        category.setName("test");
        Category resultCategory = categoryService.saveCategory(category);
        categoryService.deleteCategory(resultCategory.getCategoryId());
        Iterable<Category> allCategories = categoryService.getAllCategories();
        assertNotNull(allCategories);
        assertEquals(10, IterableUtil.sizeOf(allCategories));
    }

    @Test
    public void findAllByParentCategoryTest() {
        Iterable<Category> parentCategories = categoryService.getParentCategories();
        List<Category> parentCategoriesList = Lists.newArrayList(parentCategories);
        Category parent = parentCategoriesList.get(0);
        Iterable<Category> subcategories = categoryService.findSubcategories(parent.getCategoryId());
        assertNotNull(subcategories);
        List<Category> subcategoryList = Lists.newArrayList(subcategories);
        assertEquals("Chocolate Cake", subcategoryList.get(0).getName());
        assertEquals("Low Fat Cake", subcategoryList.get(1).getName());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }

    private Category saveParentCategory() {
        Category parentCategory = new Category();
        parentCategory.setName("Parent Category");
        return categoryService.saveCategory(parentCategory);
    }
}
