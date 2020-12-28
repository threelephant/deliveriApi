package com.delivery.api.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long id, Long menuItemId, String menuItemName, Integer quantity, BigDecimal price) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderItemResponseBuilder builder() {
        return new OrderItemResponseBuilder();
    }

    public static class OrderItemResponseBuilder {
        private Long id;
        private Long menuItemId;
        private String menuItemName;
        private Integer quantity;
        private BigDecimal price;

        public OrderItemResponseBuilder id(Long id) { this.id = id; return this; }
        public OrderItemResponseBuilder menuItemId(Long menuItemId) { this.menuItemId = menuItemId; return this; }
        public OrderItemResponseBuilder menuItemName(String menuItemName) { this.menuItemName = menuItemName; return this; }
        public OrderItemResponseBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public OrderItemResponseBuilder price(BigDecimal price) { this.price = price; return this; }

        public OrderItemResponse build() {
            return new OrderItemResponse(id, menuItemId, menuItemName, quantity, price);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
    public String getMenuItemName() { return menuItemName; }
    public void setMenuItemName(String menuItemName) { this.menuItemName = menuItemName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
