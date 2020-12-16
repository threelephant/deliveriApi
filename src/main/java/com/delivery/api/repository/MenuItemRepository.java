package com.delivery.api.repository;

import com.delivery.api.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);

    boolean existsByIdAndRestaurantId(Long id, Long restaurantId);
}
