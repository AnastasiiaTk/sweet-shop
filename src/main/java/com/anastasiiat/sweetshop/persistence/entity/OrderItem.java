package com.anastasiiat.sweetshop.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sweet_order_id")
    private SweetOrder sweetOrder;

    public Integer getOrderItemId() {
        return this.orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SweetOrder getSweetOrder() {
        return sweetOrder;
    }

    public void setSweetOrder(SweetOrder sweetOrder) {
        this.sweetOrder = sweetOrder;
    }

}
