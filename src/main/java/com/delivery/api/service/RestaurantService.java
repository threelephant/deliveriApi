package com.delivery.api.service;

import com.delivery.api.dto.RestaurantRequest;
import com.delivery.api.dto.RestaurantResponse;
import com.delivery.api.entity.Restaurant;
import com.delivery.api.entity.User;
import com.delivery.api.exception.ResourceNotFoundException;
import com.delivery.api.mapper.RestaurantMapper;
import com.delivery.api.repository.RestaurantRepository;
import com.delivery.api.repository.RestaurantSpecification;
import com.delivery.api.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "restaurants", key = "'all'")
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RestaurantResponse getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
        return restaurantMapper.toResponse(restaurant);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "restaurants", key = "#name + '-' + #minRating")
    public List<RestaurantResponse> search(String name, Double minRating) {
        Specification<Restaurant> spec = Specification
                .where(RestaurantSpecification.nameContainsIgnoreCase(name))
                .and(RestaurantSpecification.minRating(minRating));
        return restaurantRepository.findAll(spec).stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public RestaurantResponse create(RestaurantRequest request, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", ownerId));
        Restaurant restaurant = restaurantMapper.toEntity(request, owner);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(restaurant);
    }
}
