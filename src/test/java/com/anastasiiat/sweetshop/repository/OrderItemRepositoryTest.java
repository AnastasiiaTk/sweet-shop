package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.repository.OrderItemRepository;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderItemRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void findAllBySweetOrderTest() {
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1);
        Iterable<OrderItem> orderItems = orderItemRepository.findAllBySweetOrder(sweetOrder);
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
