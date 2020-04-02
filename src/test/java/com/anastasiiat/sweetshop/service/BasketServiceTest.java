package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasketServiceTest extends SweetShopApplicationTests {

    private final String SESSION_ID = "sessionId";
    @Autowired
    private BasketService basketService;

    @Test
    public void addSameProductToBasketTest() {
        Product product = new Product();
        product.setName("test");
        basketService.addToBasket(SESSION_ID, product);
        assertEquals(1, basketService.readBasketItems(SESSION_ID).size());
        assertEquals(Integer.valueOf(1), basketService.readBasketItems(SESSION_ID).get(product));
        basketService.addToBasket(SESSION_ID, product);
        assertEquals(Integer.valueOf(2), basketService.readBasketItems(SESSION_ID).get(product));
    }

    @Test
    public void addDifferentProductToBasketTest() {
        Product firstProduct = new Product();
        firstProduct.setProductId(1);
        firstProduct.setName("firstProduct");
        Product secondProduct = new Product();
        secondProduct.setProductId(2);
        secondProduct.setName("secondProduct");
        basketService.addToBasket(SESSION_ID, firstProduct);
        assertEquals(Integer.valueOf(1), basketService.readBasketItems(SESSION_ID).get(firstProduct));
        basketService.addToBasket(SESSION_ID, secondProduct);
        assertEquals(2, basketService.readBasketItems(SESSION_ID).size());
        assertEquals(Integer.valueOf(1), basketService.readBasketItems(SESSION_ID).get(secondProduct));
    }

    @Test
    public void deleteProductFromBasket() {
        Product firstProduct = new Product();
        firstProduct.setProductId(1);
        firstProduct.setName("firstProduct");
        Product secondProduct = new Product();
        secondProduct.setProductId(2);
        secondProduct.setName("secondProduct");
        basketService.addToBasket(SESSION_ID, firstProduct);
        basketService.addToBasket(SESSION_ID, secondProduct);
        assertEquals(2, basketService.readBasketItems(SESSION_ID).size());
        basketService.deleteProduct(SESSION_ID, secondProduct);
        assertEquals(1, basketService.readBasketItems(SESSION_ID).size());
    }

    @Test
    public void expirationBasketTest() throws Exception {
        Product product = new Product();
        product.setName("test");
        basketService.addToBasket(SESSION_ID, product);
        assertEquals(1, basketService.readBasketItems(SESSION_ID).size());
        Thread.sleep(3000);
        assertEquals(0, basketService.readBasketItems(SESSION_ID).size());
    }

    @Test
    public void findProductByIdFromCacheTest() {
        Product firstProduct = new Product();
        firstProduct.setProductId(1);
        firstProduct.setName("firstProduct");
        Product secondProduct = new Product();
        secondProduct.setProductId(2);
        secondProduct.setName("secondProduct");
        basketService.addToBasket(SESSION_ID, firstProduct);
        basketService.addToBasket(SESSION_ID, secondProduct);
        Product resultProduct = basketService.findProductByIdFromCache(SESSION_ID, 1);
        assertNotNull(resultProduct);
        assertEquals(Integer.valueOf(1), resultProduct.getProductId());
        assertEquals("firstProduct", resultProduct.getName());
    }

    @Test
    public void countTotalPriceTest() {
        Product firstProduct = new Product();
        firstProduct.setProductId(1);
        firstProduct.setPrice(10d);
        firstProduct.setName("firstProduct");
        Product secondProduct = new Product();
        secondProduct.setProductId(2);
        secondProduct.setName("secondProduct");
        secondProduct.setPrice(20d);
        basketService.addToBasket(SESSION_ID, firstProduct);
        basketService.addToBasket(SESSION_ID, secondProduct);
        basketService.addToBasket(SESSION_ID, secondProduct);
        Map<Product, Integer> basketItems = basketService.readBasketItems(SESSION_ID);
        assertEquals(50d, basketService.countTotalPrice(basketItems), 0.001);
    }

    @Test
    public void changeProductAmountTest() {
        Product firstProduct = new Product();
        firstProduct.setProductId(1);
        firstProduct.setPrice(10d);
        firstProduct.setName("firstProduct");
        basketService.addToBasket(SESSION_ID, firstProduct);
        basketService.changeProductAmount(SESSION_ID, firstProduct, 4);
        Map<Product, Integer> basketItems = basketService.readBasketItems(SESSION_ID);
        assertEquals(Integer.valueOf(4), basketItems.get(firstProduct));
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {
        basketService.invalidateCache();
    }
}
