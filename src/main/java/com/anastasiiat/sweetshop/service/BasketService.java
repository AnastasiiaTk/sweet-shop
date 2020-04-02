package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.cache.config.BasketCacheConfig;
import com.anastasiiat.sweetshop.cache.data.BasketData;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BasketCacheConfig basketCacheConfig;

    public void addToBasket(String sessionId, Product product) {
        try {
            BasketData data = basketCacheConfig.getCache().get(sessionId);
            data.addProduct(product);
        } catch (ExecutionException e) {
            logger.error("ERROR during adding product " + product.getProductId(), e);
        }
    }

    public void deleteProduct(String sessionId, Product product) {
        try {
            BasketData data = basketCacheConfig.getCache().get(sessionId);
            data.removeProduct(product);
        } catch (ExecutionException e) {
            logger.error("ERROR during deleting product " + product.getProductId(), e);
        }
    }

    public void changeProductAmount(String sessionId, Product product, Integer newAmount) {
        try {
            BasketData data = basketCacheConfig.getCache().get(sessionId);
            data.changeProductAmount(product, newAmount);
        } catch (ExecutionException e) {
            logger.error("ERROR during deleting product " + product.getProductId(), e);
        }
    }

    public Map<Product, Integer> readBasketItems(String sessionId) {
        try {
            return basketCacheConfig.getCache().get(sessionId).readBasketItems();
        } catch (Exception e) {
            logger.error("ERROR during reading basket data: ", e);
            return null;
        }
    }

    public double countTotalPrice(Map<Product, Integer> basketItems) {
        double result = 0d;
        for (Map.Entry<Product, Integer> entry : basketItems.entrySet()) {
            result += (entry.getKey().getPrice() * entry.getValue());
        }
        return result;
    }

    public Product findProductByIdFromCache(String sessionId, Integer productId) {
        Map<Product, Integer> basketItems = readBasketItems(sessionId);
        List<Product> filteredProducts = basketItems.keySet().stream()
                .filter(product -> product.getProductId().equals(productId))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(filteredProducts) ? null : filteredProducts.get(0);
    }

    public void invalidateCache() {
        basketCacheConfig.getCache().invalidateAll();
    }

    public BasketData getBasketData(String sessionId){
        return basketCacheConfig.getCache().getUnchecked(sessionId);
    }

}
