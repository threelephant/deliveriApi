package com.delivery.api.mapper;

import com.delivery.api.dto.RestaurantRequest;
import com.delivery.api.dto.RestaurantResponse;
import com.delivery.api.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponse toResponse(Restaurant entity) {
        if (entity == null) return null;
        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .rating(entity.getRating())
                .ownerId(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .build();
    }

    public Restaurant toEntity(RestaurantRequest request, com.delivery.api.entity.User owner) {
        if (request == null) return null;
        return Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .rating(request.getRating() != null ? request.getRating() : 0.0)
                .owner(owner)
                .build();
    }
}
