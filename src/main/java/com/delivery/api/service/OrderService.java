package com.delivery.api.service;

import com.delivery.api.dto.CreateOrderRequest;
import com.delivery.api.dto.OrderResponse;
import com.delivery.api.entity.*;
import com.delivery.api.enums.OrderStatus;
import com.delivery.api.exception.BadRequestException;
import com.delivery.api.exception.ResourceNotFoundException;
import com.delivery.api.mapper.OrderMapper;
import com.delivery.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository,
                        RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request, Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", customerId));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", request.getRestaurantId()));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem", itemReq.getMenuItemId()));
            if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
                throw new BadRequestException("MenuItem does not belong to this restaurant");
            }
            BigDecimal linePrice = menuItem.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalPrice = totalPrice.add(linePrice);
            orderItems.add(OrderItem.builder()
                    .quantity(itemReq.getQuantity())
                    .price(menuItem.getPrice())
                    .menuItem(menuItem)
                    .build());
        }

        Order order = Order.builder()
                .status(OrderStatus.PLACED)
                .totalPrice(totalPrice)
                .customer(customer)
                .restaurant(restaurant)
                .orderItems(orderItems)
                .build();
        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
        }
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getRestaurantOrders(Long ownerId) {
        return orderRepository.findByRestaurant_Owner_IdOrderByCreatedAtDesc(ownerId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> searchByStatus(OrderStatus status) {
        if (status == null) {
            return List.of();
        }
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse accept(Long orderId, Long restaurantOwnerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (!order.getRestaurant().getOwner().getId().equals(restaurantOwnerId)) {
            throw new BadRequestException("Not your restaurant's order");
        }
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new BadRequestException("Order can only be accepted when status is PLACED");
        }
        order.setStatus(OrderStatus.ACCEPTED);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse markReady(Long orderId, Long restaurantOwnerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (!order.getRestaurant().getOwner().getId().equals(restaurantOwnerId)) {
            throw new BadRequestException("Not your restaurant's order");
        }
        if (order.getStatus() != OrderStatus.ACCEPTED && order.getStatus() != OrderStatus.PREPARING) {
            throw new BadRequestException("Order can only be marked ready when status is ACCEPTED or PREPARING");
        }
        order.setStatus(OrderStatus.READY);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAvailableForDelivery() {
        return orderRepository.findByStatus(OrderStatus.READY).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse assignToSelf(Long orderId, Long deliveryPersonId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (order.getStatus() != OrderStatus.READY) {
            throw new BadRequestException("Only READY orders can be assigned");
        }
        User deliveryPerson = userRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new ResourceNotFoundException("User", deliveryPersonId));
        order.setDeliveryPerson(deliveryPerson);
        order.setStatus(OrderStatus.PICKED_UP);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse markDelivered(Long orderId, Long deliveryPersonId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (order.getDeliveryPerson() == null || !order.getDeliveryPerson().getId().equals(deliveryPersonId)) {
            throw new BadRequestException("Not your assigned order");
        }
        if (order.getStatus() != OrderStatus.PICKED_UP) {
            throw new BadRequestException("Order must be PICKED_UP to mark delivered");
        }
        order.setStatus(OrderStatus.DELIVERED);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }
}
