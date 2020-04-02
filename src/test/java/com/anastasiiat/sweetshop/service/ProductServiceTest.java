package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends SweetShopApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void getAllProductsTest() {
        Iterable<Product> allProducts = productService.getAllProducts();
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
    public void findByСategoryIdTest() {
        Iterable<Product> poductsByCategory = productService.findByСategoryId(9);
        assertNotNull(poductsByCategory);
        assertEquals(3, IterableUtil.sizeOf(poductsByCategory));
        List<Product> productList = Lists.newArrayList(poductsByCategory);
        assertEquals("Fat and Happy", productList.get(0).getName());
        assertEquals("Fat and Sweet", productList.get(1).getName());
        assertEquals("Fat bear", productList.get(2).getName());
    }

    @Test
    public void findProductByIdTest() {
        Product product = productService.findProductById(1);
        assertNotNull(product);
        assertEquals("Milka", product.getName());
        assertEquals(30.05, product.getPrice(), 0.001);
        assertEquals("/storage/milka.jpg", product.getPhotoPath());
        assertEquals("The best chocolate in your life", product.getDescription());
        assertTrue(product.getAvailable());
    }

    @Test
    public void saveNewProductTest() {
        saveProduct();
        Iterable<Product> allProducts = productService.getAllProducts();
        assertNotNull(allProducts);
        assertTrue(IterableUtil.sizeOf(allProducts) > 10);
        Product resultProduct = productService.findProductById(11);
        assertEquals("Test product", resultProduct.getName());
        assertEquals("Test description", resultProduct.getDescription());
        assertEquals(20.02, resultProduct.getPrice(), 0.001);
        assertEquals("/storage/milka.jpg", resultProduct.getPhotoPath());
        assertTrue(resultProduct.getAvailable());
    }

    @Test
    public void updateProductTest() {
        Product product = saveProduct();
        Category category = new Category();
        category.setCategoryId(5);
        product.setName("Upd test name");
        product.setDescription("Upd test description");
        product.setPhotoPath("/storage/test.jpg");
        product.setAvailable(false);
        product.setPrice(10.01);
        product.setCategory(category);
        productService.saveProduct(product);
        Iterable<Product> allProducts = productService.getAllProducts();
        assertNotNull(allProducts);
        assertEquals(11, IterableUtil.sizeOf(allProducts));
        Product resultProduct = productService.findProductById(product.getProductId());
        assertEquals("Upd test name", resultProduct.getName());
        assertEquals("Upd test description", resultProduct.getDescription());
        assertEquals(10.01, resultProduct.getPrice(), 0.001);
        assertEquals("/storage/test.jpg", resultProduct.getPhotoPath());
        assertEquals(Integer.valueOf(5), resultProduct.getCategory().getCategoryId());
        assertFalse(resultProduct.getAvailable());
    }

    @Test
    public void deleteProductTest() {
        Product product = productService.getAllProducts().iterator().next();
        productService.deleteProduct(product.getProductId());
        Iterable<Product> allProducts = productService.getAllProducts();
        assertNotNull(allProducts);
        assertEquals(9, IterableUtil.sizeOf(allProducts));
    }


    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }

    private Product saveProduct() {
        Product product = new Product();
        Category category = new Category();
        category.setCategoryId(4);
        product.setName("Test product");
        product.setDescription("Test description");
        product.setPrice(20.02);
        product.setPhotoPath("/storage/milka.jpg");
        product.setCategory(category);
        return productService.saveProduct(product);
    }
}
