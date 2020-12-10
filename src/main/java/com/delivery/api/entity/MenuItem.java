package com.delivery.api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public MenuItem() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Restaurant restaurant;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder restaurant(Restaurant restaurant) { this.restaurant = restaurant; return this; }
        public Builder orderItems(List<OrderItem> o) { this.orderItems = o != null ? o : new ArrayList<>(); return this; }

        public MenuItem build() {
            MenuItem m = new MenuItem();
            m.setId(id);
            m.setName(name);
            m.setDescription(description);
            m.setPrice(price);
            m.setRestaurant(restaurant);
            m.setOrderItems(orderItems);
            return m;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
