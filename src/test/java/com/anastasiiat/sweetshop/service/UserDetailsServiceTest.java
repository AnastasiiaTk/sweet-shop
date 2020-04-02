package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.Assert.assertEquals;

public class UserDetailsServiceTest extends SweetShopApplicationTests {

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void loadUserByUsernameTest() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertEquals("admin", userDetails.getUsername());
        assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
