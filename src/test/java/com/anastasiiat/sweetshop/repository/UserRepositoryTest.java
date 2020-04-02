package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.repository.UserRepository;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameTest() {
        User user = userRepository.findByUsername("admin");
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
