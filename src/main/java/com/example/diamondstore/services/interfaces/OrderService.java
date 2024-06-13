package com.example.diamondstore.services.interfaces;

import com.example.diamondstore.dto.CreateOrderRequestDTO;
import com.example.diamondstore.dto.OrderDTO;
import com.example.diamondstore.dto.UpdateOrderDTO;
import com.example.diamondstore.entities.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {


    void createOrderAndDetails(CreateOrderRequestDTO createOrderRequestDTO);
    Order createOrder(OrderDTO orderDTO);

    List<Order> getAllOrder();

    void deleteOrder(Integer orderId);

    Order getOrderId(Integer orderId);

    Order updateOrderByMember(UpdateOrderDTO updateOrderDTO, Integer orderId);

    List<Order> getOrdersByUserId(Integer userId);  // Đảm bảo phương thức này tồn tại
}
