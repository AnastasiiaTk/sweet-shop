package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.repository.ProductRepository;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllByOrderByNameTest() {
        Iterable<Product> allProducts = productRepository.findAllByOrderByName();
        assertNotNull(allProducts);
        assertEquals(10, IterableUtil.sizeOf(allProducts));
        List<Product> productList = Lists.newArrayList(allProducts);
        assertEquals("Apple Boom", productList.get(0).getName());
        assertEquals("Banana and Happiness", productList.get(1).getName());
        assertEquals("Cherry and Happiness", productList.get(2).getName());
        assertEquals("Fat and Happy", productList.get(3).getName());
        assertEquals("Fat and Sweet", productList.get(4).getName());
        assertEquals("Fat bear", productList.get(5).getName());
        assertEquals("Milka", productList.get(6).getName());
        assertEquals("Millennium", productList.get(7).getName());
        assertEquals("Paradise", productList.get(8).getName());
        assertEquals("White Milka", productList.get(9).getName());
    }

    @Test
    public void findAllByCategoryCategoryIdTest() {
        Iterable<Product> poductsByCategory = productRepository.findAllByCategoryId(9);
        assertNotNull(poductsByCategory);
        assertEquals(3, IterableUtil.sizeOf(poductsByCategory));
        List<Product> productList = Lists.newArrayList(poductsByCategory);
        assertEquals("Fat and Happy", productList.get(0).getName());
        assertEquals("Fat and Sweet", productList.get(1).getName());
        assertEquals("Fat bear", productList.get(2).getName());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
