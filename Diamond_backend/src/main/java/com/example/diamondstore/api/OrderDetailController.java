package com.example.diamondstore.api;


import com.example.diamondstore.dto.OrderDetailDTO;
import com.example.diamondstore.dto.UpdateOrderDetailDTO;
import com.example.diamondstore.entities.OrderDetail;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.response.ApiResponse;
import com.example.diamondstore.services.interfaces.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order_detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return new ResponseEntity<>(ApiResponse.builder()
                    .success(true)
                    .message("Order Detail Created Successfully")
                    .data(orderDetail)
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to create order detail: " + e.getMessage())
                            .build()
            );
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrderDetail(@PathVariable Integer id, @RequestBody UpdateOrderDetailDTO updateOrderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, updateOrderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Order Detail Updated Successfully")
                    .data(orderDetail)
                    .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Order detail not found: " + e.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to update order detail: " + e.getMessage())
                            .build()
            );
        }
    }


    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<ApiResponse> deleteOrderDetail(@PathVariable Integer orderDetailId) {
        try {
            orderDetailService.deleteOrderDetail(orderDetailId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Order Detail Deleted Successfully")
                    .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Order detail not found: " + e.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to delete order detail: " + e.getMessage())
                            .build()
            );
        }
    }


    @GetMapping("/{orderDetailId}")
    public ResponseEntity<ApiResponse> getOrderDetailById(@PathVariable Integer orderDetailId) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetailById(orderDetailId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Order Detail Retrieved Successfully")
                    .data(orderDetail)
                    .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Order detail not found: " + e.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to retrieve order detail: " + e.getMessage())
                            .build()
            );
        }
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOrderDetails() {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetail();
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("List of Order Details")
                    .data(orderDetails)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to retrieve order details: " + e.getMessage())
                            .build()
            );
        }
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("List of Order Details by Order ID")
                    .data(orderDetails)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Failed to retrieve order details: " + e.getMessage())
                            .build()
            );
        }
    }




//    @GetMapping("/order/{orderId}/total-price")
//    public ResponseEntity<?> calculateTotalPrice(@PathVariable Integer orderId) {
//        try {
//            float totalPrice = orderDetailService.calculateTotalPrice(orderId);
//            return ResponseEntity.ok(totalPrice);
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
//        }
//    }
}
