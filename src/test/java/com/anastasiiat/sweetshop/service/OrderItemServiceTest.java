package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderItemServiceTest extends SweetShopApplicationTests {

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void saveOrderItemTest() {
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1);
        Product productFirst = new Product();
        productFirst.setProductId(1);
        Product productSecond = new Product();
        productSecond.setProductId(1);
        OrderItem orderItemFirst = new OrderItem();
        orderItemFirst.setProduct(productFirst);
        orderItemFirst.setSweetOrder(sweetOrder);
        OrderItem orderItemSecond = new OrderItem();
        orderItemSecond.setProduct(productSecond);
        orderItemSecond.setSweetOrder(sweetOrder);
        orderItemService.saveOrderItems(Arrays.asList(orderItemFirst, orderItemSecond));
        Iterable<OrderItem> orderItems = orderItemService.getOrderItems(sweetOrder);
        assertNotNull(orderItems);
        assertEquals(6, IterableUtil.sizeOf(orderItems));
    }

    @Test
    public void getOrderItemsTest() {
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1);
        Iterable<OrderItem> orderItems = orderItemService.getOrderItems(sweetOrder);
        assertNotNull(orderItems);
        assertEquals(4, IterableUtil.sizeOf(orderItems));
    }


    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
