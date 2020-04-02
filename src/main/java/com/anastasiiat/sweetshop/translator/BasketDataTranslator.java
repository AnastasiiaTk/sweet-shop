package com.anastasiiat.sweetshop.translator;

import com.anastasiiat.sweetshop.cache.data.BasketData;
import com.anastasiiat.sweetshop.persistence.entity.OrderItem;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.entity.User;
import com.anastasiiat.sweetshop.persistence.entity.SweetOrder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BasketDataTranslator {

    public SweetOrder userBasketDataToOrder(BasketData basketData, User user) {
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setUser(user);
        Iterator iterator = basketData.readBasketItems().entrySet().iterator();
        List<OrderItem> orderItems = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Product, Integer> basketItem = (Map.Entry<Product, Integer>) iterator.next();
            for (int i = 0; i < basketItem.getValue(); i++) {
                OrderItem item = new OrderItem();
                item.setProduct(basketItem.getKey());
                item.setSweetOrder(sweetOrder);
                orderItems.add(item);
            }
        }
        sweetOrder.setOrderItems(orderItems);
        return sweetOrder;
    }

}
