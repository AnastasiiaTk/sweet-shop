package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import com.anastasiiat.sweetshop.persistence.repository.CategoryRepository;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CategoryRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllOrderByTree() {
        Iterable<Category> categories = categoryRepository.findAllOrderByTree();
        assertNotNull(categories);
        assertEquals(10, IterableUtil.sizeOf(categories));
        List<Category> categoryList = Lists.newArrayList(categories);
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
    public void findParentCategoriesTest() {
        Iterable<Category> categories = categoryRepository.findAllByParentCategoryIsNullOrderByName();
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
    public void findAllByParentCategoryTest() {
        Iterable<Category> parentCategories = categoryRepository.findAllByParentCategoryIsNullOrderByName();
        List<Category> parentCategoriesList = Lists.newArrayList(parentCategories);
        Category parent = parentCategoriesList.get(0);
        Iterable<Category> subcategories = categoryRepository.findAllByParentCategoryCategoryId(parent.getCategoryId());
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

}
