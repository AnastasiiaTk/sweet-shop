package com.anastasiiat.sweetshop.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Integer getParentCategoryId() {
        return getParentCategory() == null ? null : getParentCategory().getCategoryId();
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        if (parentCategoryId == null) {
            return;
        }
        if (this.getParentCategory() == null) {
            this.setParentCategory(new Category());
        }
        this.getParentCategory().setCategoryId(parentCategoryId);
    }

}
