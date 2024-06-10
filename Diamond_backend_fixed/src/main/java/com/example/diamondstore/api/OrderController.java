package com.example.diamondstore.api;

import com.example.diamondstore.dto.OrderDTO;
import com.example.diamondstore.dto.UpdateOrderDTO;
import com.example.diamondstore.entities.Order;
import com.example.diamondstore.response.ApiResponse;
import com.example.diamondstore.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderId(@PathVariable Integer orderId) {
        try {
            Order order = orderService.getOrderId(orderId);
            if (order != null) {
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .message("Get Order by Id Success~")
                        .data(order)
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Can't find any order with id = " + orderId)
                                .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to get order: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order saveOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.builder()
                            .success(true)
                            .message("Order Created Successfully")
                            .data(saveOrder)
                            .build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to create order due to data integrity violation: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to create order: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrder();
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("List of Orders:")
                    .data(orders)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to list orders: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<ApiResponse> updateOrder(@Valid @RequestBody UpdateOrderDTO updateOrderDTO, @PathVariable Integer orderId) {
        try {
            Order existingOrder = orderService.getOrderId(orderId);
            if (existingOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Update failed: Can't find this order")
                                .build());
            }

            Order updatedOrder = orderService.updateOrderByMember(updateOrderDTO, orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Order Updated Successfully!")
                    .data(updatedOrder)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Update failed: " + e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Order Deleted Successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to delete order: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Integer userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("List of Orders by User ID:")
                    .data(orders)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to list orders: " + e.getMessage())
                            .build());
        }
    }
}
