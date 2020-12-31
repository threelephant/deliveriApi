package com.delivery.api.mapper;

import com.delivery.api.dto.OrderItemResponse;
import com.delivery.api.dto.OrderResponse;
import com.delivery.api.entity.Order;
import com.delivery.api.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order entity) {
        if (entity == null) return null;
        return OrderResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .createdAt(entity.getCreatedAt())
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .restaurantId(entity.getRestaurant() != null ? entity.getRestaurant().getId() : null)
                .deliveryPersonId(entity.getDeliveryPerson() != null ? entity.getDeliveryPerson().getId() : null)
                .items(entity.getOrderItems() != null
                        ? entity.getOrderItems().stream().map(this::toOrderItemResponse).collect(Collectors.toList())
                        : null)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem entity) {
        if (entity == null) return null;
        return OrderItemResponse.builder()
                .id(entity.getId())
                .menuItemId(entity.getMenuItem() != null ? entity.getMenuItem().getId() : null)
                .menuItemName(entity.getMenuItem() != null ? entity.getMenuItem().getName() : null)
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .build();
    }
}
