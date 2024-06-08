
package com.example.diamondstore.services;

import com.example.diamondstore.dto.OrderDTO;
import com.example.diamondstore.dto.UpdateOrderDTO;
import com.example.diamondstore.entities.Order;
import com.example.diamondstore.repositories.OrderRepository;
import com.example.diamondstore.repositories.UserRepository;
import com.example.diamondstore.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order saveOrder = orderRepository.save(Order.builder()
                .cname(orderDTO.getCname())
                .email(orderDTO.getEmail())
                .address(orderDTO.getAddress())
                .payment_method(orderDTO.getPayment_method())
                .build());

        return saveOrder;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getOrderId(Integer orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public Order updateOrderByMember(UpdateOrderDTO updateOrderDTO, Integer orderId) {
        Order saveOrder = orderRepository.findByOrderId(orderId);
        saveOrder.setCname(updateOrderDTO.getCname());
        saveOrder.setAddress(updateOrderDTO.getAddress());
        saveOrder.setEmail(updateOrderDTO.getEmail());
        saveOrder.setPhone(updateOrderDTO.getPhone());
        saveOrder.setPayment_method(updateOrderDTO.getPayment_method());

        return  orderRepository.save(saveOrder);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByCid(userRepository.findUserByUserId(userId));
    }


}
