package com.anastasiiat.sweetshop.cache.data;

import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class BasketData {

    private final Integer INITIAL_AMOUNT = 1;

    private Map<Product, Integer> basketItems = new HashMap<>();

    public ImmutableMap<Product, Integer> readBasketItems() {
        return ImmutableMap.copyOf(basketItems);
    }

    public void addProduct(Product product) {
        if (basketItems.containsKey(product)) {
            changeProductAmount(product, basketItems.get(product) + 1);
            return;
        }
        basketItems.put(product, INITIAL_AMOUNT);
    }

    public void removeProduct(Product product) {
        basketItems.remove(product);
    }

    public void changeProductAmount(Product product, Integer newAmount) {
        if (basketItems.containsKey(product)) {
            Integer productAmount = basketItems.get(product);
            basketItems.replace(product, productAmount, newAmount);
        }
    }
}
