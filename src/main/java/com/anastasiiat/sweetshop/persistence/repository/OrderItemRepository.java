package com.anastasiiat.sweetshop.persistence.repository;

import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    Iterable<OrderItem> findAllBySweetOrder(SweetOrder sweetOrder);

    Iterable<OrderItem> findAllByProductProductId(Integer productId);
}
