package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;
import static org.springframework.util.ResourceUtils.getFile;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductRepository productRepository;

    private final String STORAGE_FOLDER = "/storage/";

    private final Character SEPARATOR = '.';

    private String fullPathToStorage;

    public ProductService() {
        this.fullPathToStorage = getPathToStorage();
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAllByOrderByName();
    }

    public Iterable<Product> findByСategoryId(Integer categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public String getFullPathToStorage() {
        return this.fullPathToStorage;
    }

    public String buildUniquePhotoPath(String originalName) {
        int lastDot = originalName.lastIndexOf(SEPARATOR);
        StringBuilder result = new StringBuilder();
        result.append(STORAGE_FOLDER);
        result.append(originalName.substring(0, lastDot));
        result.append(new Date().getTime());
        result.append(originalName.substring(lastDot));
        return result.toString();
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    private String getPathToStorage() {
        try {
            return getFile(CLASSPATH_URL_PREFIX + "static").getPath();
        } catch (Exception e) {
            logger.error("ERROR during creation of path to storage folder", e);
            return null;
        }
    }
}
