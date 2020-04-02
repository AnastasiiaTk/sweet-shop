package com.anastasiiat.sweetshop.repository;

import com.anastasiiat.sweetshop.SweetShopApplicationTests;
import com.anastasiiat.sweetshop.persistence.entity.Role;
import com.anastasiiat.sweetshop.persistence.repository.RoleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RoleRepositoryTest extends SweetShopApplicationTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByNameTest() {
        Role role = roleRepository.findByName("ROLE_USER");
        assertNotNull(role);
        assertEquals("ROLE_USER", role.getName());
    }

    @Override
    protected void setUp() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {

    }
}
