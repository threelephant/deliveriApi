package com.delivery.api.dto;

import java.math.BigDecimal;

public class MenuItemResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long restaurantId;

    public MenuItemResponse() {
    }

    public MenuItemResponse(Long id, String name, String description, BigDecimal price, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public static MenuItemResponseBuilder builder() {
        return new MenuItemResponseBuilder();
    }

    public static class MenuItemResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Long restaurantId;

        public MenuItemResponseBuilder id(Long id) { this.id = id; return this; }
        public MenuItemResponseBuilder name(String name) { this.name = name; return this; }
        public MenuItemResponseBuilder description(String description) { this.description = description; return this; }
        public MenuItemResponseBuilder price(BigDecimal price) { this.price = price; return this; }
        public MenuItemResponseBuilder restaurantId(Long restaurantId) { this.restaurantId = restaurantId; return this; }

        public MenuItemResponse build() {
            return new MenuItemResponse(id, name, description, price, restaurantId);
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
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
}
