package com.anastasiiat.sweetshop.persistence.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "sweet_order")
public class SweetOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sweet_order_id")
    private Integer sweetOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sweetOrder", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<OrderItem> orderItems;

    @Column(name = "created_date", nullable = false, columnDefinition = "date default sysdate")
    private Date createdDate = new Date();

    @Transient
    private Map<Product, Long> groupedProducts;

    public Integer getSweetOrderId() {
        return sweetOrderId;
    }

    public void setSweetOrderId(Integer sweetOrderId) {
        this.sweetOrderId = sweetOrderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Map<Product, Long> getGroupedProducts() {
        return groupedProducts;
    }

    public void setGroupedProducts(Map<Product, Long> groupedProducts) {
        this.groupedProducts = groupedProducts;
    }
}
