package com.delivery.api.repository;

import com.delivery.api.entity.Order;
import com.delivery.api.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    List<Order> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);

    List<Order> findByRestaurant_Owner_IdOrderByCreatedAtDesc(Long ownerId);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findByIdAndRestaurantId(Long id, Long restaurantId);

    Optional<Order> findByIdAndDeliveryPersonId(Long id, Long deliveryPersonId);
}
