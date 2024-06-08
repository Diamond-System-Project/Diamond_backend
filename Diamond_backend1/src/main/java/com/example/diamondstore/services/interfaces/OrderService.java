package com.example.diamondstore.services.interfaces;


import com.example.diamondstore.entities.Order;
import com.example.diamondstore.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    Order saveOrder(Order order);

    List<Order> getAllOrder();

    void deleteOrder(int orderid);

    Optional<Order> getOrderId(int orderid);

    void updateOrderbyMember(String cname, String address, String phone, String email, String paymentMethod, Integer orderId);



//    List<Order> findOrdersbycustomerid(int cid);




}
