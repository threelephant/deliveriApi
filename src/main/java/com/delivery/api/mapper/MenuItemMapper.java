package com.delivery.api.mapper;

import com.delivery.api.dto.MenuItemRequest;
import com.delivery.api.dto.MenuItemResponse;
import com.delivery.api.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItemResponse toResponse(MenuItem entity) {
        if (entity == null) return null;
        return MenuItemResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .restaurantId(entity.getRestaurant() != null ? entity.getRestaurant().getId() : null)
                .build();
    }

    public MenuItem toEntity(MenuItemRequest request, com.delivery.api.entity.Restaurant restaurant) {
        if (request == null) return null;
        return MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .restaurant(restaurant)
                .build();
    }
}
