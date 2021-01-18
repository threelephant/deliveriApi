package com.delivery.api.service;

import com.delivery.api.dto.MenuItemRequest;
import com.delivery.api.dto.MenuItemResponse;
import com.delivery.api.entity.MenuItem;
import com.delivery.api.entity.Restaurant;
import com.delivery.api.exception.BadRequestException;
import com.delivery.api.exception.ResourceNotFoundException;
import com.delivery.api.mapper.MenuItemMapper;
import com.delivery.api.repository.MenuItemRepository;
import com.delivery.api.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponse> getByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuItemResponse getById(Long restaurantId, Long itemId) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", itemId));
        if (!item.getRestaurant().getId().equals(restaurantId)) {
            throw new ResourceNotFoundException("MenuItem", itemId);
        }
        return menuItemMapper.toResponse(item);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public MenuItemResponse add(Long restaurantId, MenuItemRequest request, Long ownerId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", restaurantId));
        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new BadRequestException("Not the owner of this restaurant");
        }
        MenuItem item = menuItemMapper.toEntity(request, restaurant);
        item = menuItemRepository.save(item);
        return menuItemMapper.toResponse(item);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public MenuItemResponse update(Long restaurantId, Long itemId, MenuItemRequest request, Long ownerId) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", itemId));
        if (!item.getRestaurant().getId().equals(restaurantId)) {
            throw new ResourceNotFoundException("MenuItem", itemId);
        }
        if (!item.getRestaurant().getOwner().getId().equals(ownerId)) {
            throw new BadRequestException("Not the owner of this restaurant");
        }
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item = menuItemRepository.save(item);
        return menuItemMapper.toResponse(item);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(Long restaurantId, Long itemId, Long ownerId) {
        if (!menuItemRepository.existsByIdAndRestaurantId(itemId, restaurantId)) {
            throw new ResourceNotFoundException("MenuItem", itemId);
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", restaurantId));
        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new BadRequestException("Not the owner of this restaurant");
        }
        menuItemRepository.deleteById(itemId);
    }
}
