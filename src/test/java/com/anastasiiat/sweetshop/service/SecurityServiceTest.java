package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SecurityServiceTest extends SweetShopApplicationTests {

    @Autowired
    private SecurityService securityService;

    @Test
    public void findLoggedInUsernameTest() {
        securityService.autoLogin("admin", "admin");
        assertEquals("admin", securityService.findLoggedInUsername());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
