package com.delivery.api.controller;

import com.delivery.api.dto.ApiResponse;
import com.delivery.api.dto.CreateOrderRequest;
import com.delivery.api.dto.OrderResponse;
import com.delivery.api.enums.OrderStatus;
import com.delivery.api.security.JwtPrincipal;
import com.delivery.api.service.OrderService;
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
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Order flow by role")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(operationId = "createOrder", summary = "Create order (CUSTOMER only)")
    public ResponseEntity<ApiResponse<OrderResponse>> create(
            @Valid @RequestBody CreateOrderRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        OrderResponse response = orderService.create(request, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Order created", response));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(operationId = "getMyOrders", summary = "View my orders (CUSTOMER only)")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> myOrders(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        List<OrderResponse> list = orderService.getMyOrders(principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/restaurant")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "getRestaurantOrders", summary = "View restaurant orders (RESTAURANT only)")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> restaurantOrders(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        List<OrderResponse> list = orderService.getRestaurantOrders(principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @PatchMapping("/{id}/accept")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "acceptOrder", summary = "Accept order (RESTAURANT only)")
    public ResponseEntity<ApiResponse<OrderResponse>> accept(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        OrderResponse response = orderService.accept(id, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Order accepted", response));
    }

    @PatchMapping("/{id}/ready")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(operationId = "markOrderReady", summary = "Mark order ready (RESTAURANT only)")
    public ResponseEntity<ApiResponse<OrderResponse>> markReady(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        OrderResponse response = orderService.markReady(id, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Order ready", response));
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('DELIVERY')")
    @Operation(operationId = "getAvailableOrders", summary = "View available READY orders (DELIVERY only)")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> available(
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        List<OrderResponse> list = orderService.getAvailableForDelivery();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasRole('DELIVERY')")
    @Operation(operationId = "assignOrder", summary = "Assign order to self (DELIVERY only)")
    public ResponseEntity<ApiResponse<OrderResponse>> assign(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        OrderResponse response = orderService.assignToSelf(id, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Order assigned", response));
    }

    @PatchMapping("/{id}/deliver")
    @PreAuthorize("hasRole('DELIVERY')")
    @Operation(operationId = "markOrderDelivered", summary = "Mark order delivered (DELIVERY only)")
    public ResponseEntity<ApiResponse<OrderResponse>> deliver(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        OrderResponse response = orderService.markDelivered(id, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Order delivered", response));
    }

    @GetMapping("/search")
    @Operation(operationId = "searchOrders", summary = "Search orders by status")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> search(
            @RequestParam(required = false) OrderStatus status,
            @Parameter(hidden = true) @AuthenticationPrincipal JwtPrincipal principal) {
        List<OrderResponse> list = orderService.searchByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
}
