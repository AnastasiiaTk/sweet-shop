package com.anastasiiat.sweetshop.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available", nullable = false, columnDefinition = "boolean default true")
    private Boolean available = true;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return this.category == null ? null : this.category.getCategoryId();
    }

    public void setCategoryId(Integer categoryId) {
        if (this.category == null) {
            this.setCategory(new Category());
        }
        this.getCategory().setCategoryId(categoryId);
    }

    public Integer getParentCategoryId() {
        return getCategory() == null ? null : getCategory().getParentCategoryId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return Objects.equals(productId, product.getProductId());
    }
}
