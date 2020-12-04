package com.delivery.api.entity;

import com.delivery.api.enums.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> ordersAsCustomer = new ArrayList<>();

    @OneToMany(mappedBy = "deliveryPerson")
    private List<Order> ordersAsDelivery = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email, String password, Role role,
               List<Restaurant> restaurants, List<Order> ordersAsCustomer, List<Order> ordersAsDelivery) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.restaurants = restaurants != null ? restaurants : new ArrayList<>();
        this.ordersAsCustomer = ordersAsCustomer != null ? ordersAsCustomer : new ArrayList<>();
        this.ordersAsDelivery = ordersAsDelivery != null ? ordersAsDelivery : new ArrayList<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private Role role;
        private List<Restaurant> restaurants = new ArrayList<>();
        private List<Order> ordersAsCustomer = new ArrayList<>();
        private List<Order> ordersAsDelivery = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder restaurants(List<Restaurant> r) { this.restaurants = r != null ? r : new ArrayList<>(); return this; }
        public Builder ordersAsCustomer(List<Order> o) { this.ordersAsCustomer = o != null ? o : new ArrayList<>(); return this; }
        public Builder ordersAsDelivery(List<Order> o) { this.ordersAsDelivery = o != null ? o : new ArrayList<>(); return this; }

        public User build() {
            return new User(id, name, email, password, role, restaurants, ordersAsCustomer, ordersAsDelivery);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public List<Restaurant> getRestaurants() { return restaurants; }
    public void setRestaurants(List<Restaurant> restaurants) { this.restaurants = restaurants; }
    public List<Order> getOrdersAsCustomer() { return ordersAsCustomer; }
    public void setOrdersAsCustomer(List<Order> ordersAsCustomer) { this.ordersAsCustomer = ordersAsCustomer; }
    public List<Order> getOrdersAsDelivery() { return ordersAsDelivery; }
    public void setOrdersAsDelivery(List<Order> ordersAsDelivery) { this.ordersAsDelivery = ordersAsDelivery; }
}
