package com.anastasiiat.sweetshop.validator;

import com.anastasiiat.sweetshop.error.InvalidProductOperationException;
import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.service.OrderItemService;
import com.anastasiiat.sweetshop.util.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private Environment environment;

    public void validateToDelete(Integer productId) throws InvalidProductOperationException {
        Iterable<OrderItem> orderItems = orderItemService.findOrderItemsByProduct(productId);
        if (IterableUtils.isNotEmpty(orderItems)) {
            throw new InvalidProductOperationException(environment.getProperty("product.is.ordered.error"));
        }
    }
}
