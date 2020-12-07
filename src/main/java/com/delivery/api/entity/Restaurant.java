package com.delivery.api.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double rating = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders = new ArrayList<>();

    public Restaurant() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private Double rating = 0.0;
        private User owner;
        private List<MenuItem> menuItems = new ArrayList<>();
        private List<Order> orders = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder rating(Double rating) { this.rating = rating != null ? rating : 0.0; return this; }
        public Builder owner(User owner) { this.owner = owner; return this; }
        public Builder menuItems(List<MenuItem> m) { this.menuItems = m != null ? m : new ArrayList<>(); return this; }
        public Builder orders(List<Order> o) { this.orders = o != null ? o : new ArrayList<>(); return this; }

        public Restaurant build() {
            Restaurant r = new Restaurant();
            r.setId(id);
            r.setName(name);
            r.setAddress(address);
            r.setRating(rating);
            r.setOwner(owner);
            r.setMenuItems(menuItems);
            r.setOrders(orders);
            return r;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public List<MenuItem> getMenuItems() { return menuItems; }
    public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
