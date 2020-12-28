package com.delivery.api.dto;

import com.delivery.api.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryPersonId;
    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public static OrderResponseBuilder builder() {
        return new OrderResponseBuilder();
    }

    public static class OrderResponseBuilder {
        private Long id;
        private OrderStatus status;
        private BigDecimal totalPrice;
        private LocalDateTime createdAt;
        private Long customerId;
        private Long restaurantId;
        private Long deliveryPersonId;
        private List<OrderItemResponse> items;

        public OrderResponseBuilder id(Long id) { this.id = id; return this; }
        public OrderResponseBuilder status(OrderStatus status) { this.status = status; return this; }
        public OrderResponseBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public OrderResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderResponseBuilder customerId(Long customerId) { this.customerId = customerId; return this; }
        public OrderResponseBuilder restaurantId(Long restaurantId) { this.restaurantId = restaurantId; return this; }
        public OrderResponseBuilder deliveryPersonId(Long deliveryPersonId) { this.deliveryPersonId = deliveryPersonId; return this; }
        public OrderResponseBuilder items(List<OrderItemResponse> items) { this.items = items; return this; }

        public OrderResponse build() {
            OrderResponse r = new OrderResponse();
            r.setId(id);
            r.setStatus(status);
            r.setTotalPrice(totalPrice);
            r.setCreatedAt(createdAt);
            r.setCustomerId(customerId);
            r.setRestaurantId(restaurantId);
            r.setDeliveryPersonId(deliveryPersonId);
            r.setItems(items);
            return r;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public Long getDeliveryPersonId() { return deliveryPersonId; }
    public void setDeliveryPersonId(Long deliveryPersonId) { this.deliveryPersonId = deliveryPersonId; }
    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }
}
