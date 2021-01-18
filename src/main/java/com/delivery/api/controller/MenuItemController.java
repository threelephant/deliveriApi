package com.delivery.api.controller;

import com.delivery.api.dto.ApiResponse;
import com.delivery.api.dto.MenuItemRequest;
import com.delivery.api.dto.MenuItemResponse;
import com.delivery.api.security.JwtPrincipal;
import com.delivery.api.service.MenuItemService;
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
@RequestMapping("/restaurants/{restaurantId}/menu")
@Tag(name = "Menu items", description = "Restaurant menu CRUD")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    @Operation(operationId = "listMenuItems", summary = "List menu items for a restaurant")
    public ResponseEntity<ApiResponse<List<MenuItemResponse>>> list(@PathVariable Long restaurantId) {
        List<MenuItemResponse> list = menuItemService.getByRestaurantId(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{itemId}")
    @Operation(operationId = "getMenuItemById", summary = "Get menu item by ID")
    public ResponseEntity<ApiResponse<MenuItemResponse>> getById(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId) {
        MenuItemResponse response = menuItemService.getById(restaurantId, itemId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "addMenuItem", summary = "Add menu item (restaurant owner only)")
    public ResponseEntity<ApiResponse<MenuItemResponse>> add(
            @PathVariable Long restaurantId,
            @Valid @RequestBody MenuItemRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        MenuItemResponse response = menuItemService.add(restaurantId, request, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Menu item added", response));
    }

    @PutMapping("/{itemId}")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "updateMenuItem", summary = "Update menu item (restaurant owner only)")
    public ResponseEntity<ApiResponse<MenuItemResponse>> update(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @Valid @RequestBody MenuItemRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        MenuItemResponse response = menuItemService.update(restaurantId, itemId, request, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "deleteMenuItem", summary = "Delete menu item (restaurant owner only)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        menuItemService.delete(restaurantId, itemId, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Menu item deleted", null));
    }
}
