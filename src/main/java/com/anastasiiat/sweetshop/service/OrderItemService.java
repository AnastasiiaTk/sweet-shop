package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import com.anastasiiat.sweetshop.persistence.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void saveOrderItems(Iterable<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            orderItemRepository.save(item);
        }
    }

    public Iterable<OrderItem> getOrderItems(SweetOrder sweetOrder) {
        return orderItemRepository.findAllBySweetOrder(sweetOrder);
    }

    public Iterable<OrderItem> findOrderItemsByProduct(Integer productId) {
        return orderItemRepository.findAllByProductProductId(productId);
    }
}
