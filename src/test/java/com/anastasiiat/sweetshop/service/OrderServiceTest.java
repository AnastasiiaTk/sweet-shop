package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.Lists;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceTest extends SweetShopApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasketService basketService;

    private final String SESSION_ID = "sessionId";

    @Test
    public void saveOrderFromBasketTest() {
        User user = userService.findByUsername("testuser");
        orderService.saveOrderFromBasket(basketService.getBasketData(SESSION_ID), user);
        Iterable<SweetOrder> resultOrders = orderService.findOrdersWithItemsByUser(user);
        assertNotNull(resultOrders);
        assertTrue(IterableUtil.sizeOf(resultOrders) > 2);
        List<SweetOrder> resultOrdersList = Lists.newArrayList(resultOrders);
        SweetOrder resultOrder = resultOrdersList.get(2);
        assertEquals(user.getUsername(), resultOrder.getUser().getUsername());
        assertEquals(4, resultOrder.getGroupedProducts().size());
    }

    @Test
    public void findOrdersWithItemsByUserTest(){
        User user = userService.findByUsername("testuser");
        Iterable<SweetOrder> resultOrders = orderService.findOrdersWithItemsByUser(user);
        assertNotNull(resultOrders);
        assertTrue(IterableUtil.sizeOf(resultOrders) == 2);
    }

    @Override
    protected void setUp() throws Exception {
        List<Product> products = Lists.newArrayList(productService.getAllProducts());
        basketService.addToBasket(SESSION_ID, products.get(0));
        basketService.addToBasket(SESSION_ID, products.get(0));
        basketService.addToBasket(SESSION_ID, products.get(0));
        basketService.addToBasket(SESSION_ID, products.get(1));
        basketService.addToBasket(SESSION_ID, products.get(2));
        basketService.addToBasket(SESSION_ID, products.get(2));
        basketService.addToBasket(SESSION_ID, products.get(4));
    }

    @Override
    protected void tearDown() throws Exception {

    }
}
