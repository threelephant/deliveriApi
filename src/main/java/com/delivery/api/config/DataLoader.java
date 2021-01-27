package com.delivery.api.config;

import com.delivery.api.entity.*;
import com.delivery.api.enums.OrderStatus;
import com.delivery.api.enums.Role;
import com.delivery.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private static final String SEED_PASSWORD = "password123";

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository,
                      RestaurantRepository restaurantRepository,
                      MenuItemRepository menuItemRepository,
                      OrderRepository orderRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("restaurant@test.com").isPresent()) {
            return;
        }

        String encodedPassword = passwordEncoder.encode(SEED_PASSWORD);

        User restaurantUser = userRepository.save(User.builder()
                .name("Restaurant Owner")
                .email("restaurant@test.com")
                .password(encodedPassword)
                .role(Role.RESTAURANT)
                .build());

        User customer1 = userRepository.save(User.builder()
                .name("Customer One")
                .email("customer1@test.com")
                .password(encodedPassword)
                .role(Role.CUSTOMER)
                .build());

        User customer2 = userRepository.save(User.builder()
                .name("Customer Two")
                .email("customer2@test.com")
                .password(encodedPassword)
                .role(Role.CUSTOMER)
                .build());

        User deliveryUser = userRepository.save(User.builder()
                .name("Delivery Person")
                .email("delivery@test.com")
                .password(encodedPassword)
                .role(Role.DELIVERY)
                .build());

        Restaurant restaurant1 = restaurantRepository.save(Restaurant.builder()
                .name("Pizza Palace")
                .address("123 Main St")
                .rating(4.5)
                .owner(restaurantUser)
                .build());

        Restaurant restaurant2 = restaurantRepository.save(Restaurant.builder()
                .name("Burger Barn")
                .address("456 Oak Ave")
                .rating(4.2)
                .owner(restaurantUser)
                .build());

        MenuItem pizza1 = menuItemRepository.save(MenuItem.builder()
                .name("Margherita")
                .description("Tomato, mozzarella, basil")
                .price(new BigDecimal("12.99"))
                .restaurant(restaurant1)
                .build());
        MenuItem pizza2 = menuItemRepository.save(MenuItem.builder()
                .name("Pepperoni")
                .description("Tomato, mozzarella, pepperoni")
                .price(new BigDecimal("14.99"))
                .restaurant(restaurant1)
                .build());
        MenuItem pizza3 = menuItemRepository.save(MenuItem.builder()
                .name("Hawaiian")
                .description("Tomato, mozzarella, ham, pineapple")
                .price(new BigDecimal("13.99"))
                .restaurant(restaurant1)
                .build());

        MenuItem burger1 = menuItemRepository.save(MenuItem.builder()
                .name("Classic Burger")
                .description("Beef patty, lettuce, tomato, onion")
                .price(new BigDecimal("9.99"))
                .restaurant(restaurant2)
                .build());
        MenuItem burger2 = menuItemRepository.save(MenuItem.builder()
                .name("Cheese Burger")
                .description("Beef patty, cheddar, lettuce, tomato")
                .price(new BigDecimal("10.99"))
                .restaurant(restaurant2)
                .build());
        MenuItem burger3 = menuItemRepository.save(MenuItem.builder()
                .name("Double Burger")
                .description("Two beef patties, cheese, special sauce")
                .price(new BigDecimal("14.99"))
                .restaurant(restaurant2)
                .build());

        // Order 1: PLACED, customer1, restaurant1, 2x Margherita + 1x Pepperoni
        BigDecimal total1 = new BigDecimal("12.99").multiply(BigDecimal.valueOf(2))
                .add(new BigDecimal("14.99"));
        Order order1 = Order.builder()
                .status(OrderStatus.PLACED)
                .totalPrice(total1)
                .customer(customer1)
                .restaurant(restaurant1)
                .build();
        List<OrderItem> items1 = List.of(
                OrderItem.builder().quantity(2).price(new BigDecimal("12.99")).order(order1).menuItem(pizza1).build(),
                OrderItem.builder().quantity(1).price(new BigDecimal("14.99")).order(order1).menuItem(pizza2).build()
        );
        order1.setOrderItems(new ArrayList<>(items1));
        orderRepository.save(order1);

        // Order 2: DELIVERED, customer2, restaurant2, deliveryUser assigned
        BigDecimal total2 = new BigDecimal("9.99").add(new BigDecimal("10.99"));
        Order order2 = Order.builder()
                .status(OrderStatus.DELIVERED)
                .totalPrice(total2)
                .customer(customer2)
                .restaurant(restaurant2)
                .deliveryPerson(deliveryUser)
                .build();
        List<OrderItem> items2 = List.of(
                OrderItem.builder().quantity(1).price(new BigDecimal("9.99")).order(order2).menuItem(burger1).build(),
                OrderItem.builder().quantity(1).price(new BigDecimal("10.99")).order(order2).menuItem(burger2).build()
        );
        order2.setOrderItems(new ArrayList<>(items2));
        orderRepository.save(order2);
    }
}
