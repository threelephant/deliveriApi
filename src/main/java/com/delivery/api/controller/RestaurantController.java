package com.delivery.api.controller;

import com.delivery.api.dto.ApiResponse;
import com.delivery.api.dto.RestaurantRequest;
import com.delivery.api.dto.RestaurantResponse;
import com.delivery.api.security.JwtPrincipal;
import com.delivery.api.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurants", description = "Restaurant CRUD and search")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "createRestaurant", summary = "Create restaurant (RESTAURANT only)")
    public ResponseEntity<ApiResponse<RestaurantResponse>> create(
            @Valid @RequestBody RestaurantRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        RestaurantResponse response = restaurantService.create(request, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Restaurant created", response));
    }

    @GetMapping
    @Operation(operationId = "getAllRestaurants", summary = "List all restaurants")
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getAll() {
        List<RestaurantResponse> list = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/search")
    @Operation(operationId = "searchRestaurants", summary = "Search restaurants by name and/or min rating")
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minRating) {
        List<RestaurantResponse> list = restaurantService.search(name, minRating);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getRestaurantById", summary = "Get restaurant by ID")
    public ResponseEntity<ApiResponse<RestaurantResponse>> getById(@PathVariable Long id) {
        RestaurantResponse response = restaurantService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
