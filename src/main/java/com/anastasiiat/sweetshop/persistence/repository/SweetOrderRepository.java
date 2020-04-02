package com.anastasiiat.sweetshop.persistence.repository;

import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SweetOrderRepository extends JpaRepository<SweetOrder, Integer> {

    Set<SweetOrder> findAllByUserOrderByCreatedDate(User user);

}
