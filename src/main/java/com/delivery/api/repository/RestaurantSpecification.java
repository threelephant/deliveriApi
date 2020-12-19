package com.delivery.api.repository;

import com.delivery.api.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public final class RestaurantSpecification {

    private RestaurantSpecification() {
    }

    public static Specification<Restaurant> nameContainsIgnoreCase(String name) {
        return (root, query, cb) -> name == null || name.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Restaurant> minRating(Double minRating) {
        return (root, query, cb) -> minRating == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("rating"), minRating);
    }
}
