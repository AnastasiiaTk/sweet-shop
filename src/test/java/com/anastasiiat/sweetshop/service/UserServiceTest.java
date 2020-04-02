package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceTest extends SweetShopApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void saveUserTest() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        User result = userService.save(user);
        assertNotNull(result);
        assertEquals("test", result.getUsername());
        assertFalse(result.getRoles().isEmpty());
        assertEquals("ROLE_USER", result.getRoles().iterator().next().getName());
    }

    @Test
    public void findByUsernameTest() {
        User user = userService.findByUsername("admin");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertFalse(user.getRoles().isEmpty());
        assertEquals("ROLE_ADMIN", user.getRoles().iterator().next().getName());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
