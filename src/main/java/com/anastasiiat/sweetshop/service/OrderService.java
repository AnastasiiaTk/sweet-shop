package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.cache.data.BasketData;
import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import com.anastasiiat.sweetshop.persistence.entity.User;
import com.anastasiiat.sweetshop.persistence.repository.SweetOrderRepository;
import com.anastasiiat.sweetshop.translator.BasketDataTranslator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SweetOrderRepository orderRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    private BasketDataTranslator basketDataTranslator;

    @Transactional
    public void saveOrderFromBasket(BasketData basketData, User user) {
        SweetOrder sweetOrder = basketDataTranslator.userBasketDataToOrder(basketData, user);
        SweetOrder savedOrder = orderRepository.save(sweetOrder);
        orderItemService.saveOrderItems(savedOrder.getOrderItems());
    }

    public Iterable<SweetOrder> findOrdersWithItemsByUser(User user) {
        Iterable<SweetOrder> result = orderRepository.findAllByUserOrderByCreatedDate(user);
        Iterator iterator = result.iterator();
        while (iterator.hasNext()) {
            SweetOrder sweetOrder = (SweetOrder) iterator.next();
            List<OrderItem> orderItemList = Lists.newArrayList(orderItemService.getOrderItems(sweetOrder));
            sweetOrder.setGroupedProducts(groupByProduct(orderItemList));
        }
        return result;
    }

    private Map<Product, Long> groupByProduct(List<OrderItem> orderItems) {
        return orderItems.stream().collect(Collectors.groupingBy(OrderItem::getProduct, Collectors.counting()));
    }

}
