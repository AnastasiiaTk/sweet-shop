package com.anastasiiat.sweetshop.persistence.repository;

import com.anastasiiat.sweetshop.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
