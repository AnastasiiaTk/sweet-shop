package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.repository.SweetOrderRepository;
import com.anastasiiat.sweetshop.service.UserService;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SweetOrderRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private SweetOrderRepository sweetOrderRepository;

    @Autowired
    private UserService userService;

    @Test
    public void findAllByUserTest() {
        User user = userService.findByUsername("testuser");
        Iterable<SweetOrder> userOrders = sweetOrderRepository.findAllByUserOrderByCreatedDate(user);
        assertNotNull(userOrders);
        assertEquals(2, IterableUtil.sizeOf(userOrders));
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
