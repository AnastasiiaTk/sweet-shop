package com.anastasiiat.sweetshop.persistence.repository;


import com.anastasiiat.sweetshop.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
