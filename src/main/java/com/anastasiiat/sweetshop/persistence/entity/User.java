package com.anastasiiat.sweetshop.persistence.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<SweetOrder> sweetOrders;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<SweetOrder> getSweetOrders() {
        return sweetOrders;
    }

    public void setSweetOrders(Set<SweetOrder> sweetOrders) {
        this.sweetOrders = sweetOrders;
    }
}
