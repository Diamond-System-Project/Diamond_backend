
package com.example.diamondstore.services;

import com.example.diamondstore.dto.CreateOrderRequestDTO;
import com.example.diamondstore.dto.OrderDTO;
import com.example.diamondstore.dto.OrderDetailDTO;
import com.example.diamondstore.dto.UpdateOrderDTO;
import com.example.diamondstore.entities.Order;
import com.example.diamondstore.entities.OrderDetail;
import com.example.diamondstore.entities.Product;
import com.example.diamondstore.repositories.OrderDetailRepository;
import com.example.diamondstore.repositories.OrderRepository;
import com.example.diamondstore.repositories.ProductRepository;
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
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrderAndDetails(CreateOrderRequestDTO createOrderRequestDTO) {
        Order order = new Order();
        order.setCname(createOrderRequestDTO.getOrder().getCname());
        order.setPhone(createOrderRequestDTO.getOrder().getPhone());
        order.setEmail(createOrderRequestDTO.getOrder().getEmail());
        order.setAddress(createOrderRequestDTO.getOrder().getAddress());
        order.setPayment_method(createOrderRequestDTO.getOrder().getPayment_method());
        order.setStatus("Pending...");

        Order savedOrder = orderRepository.save(order);

        List<OrderDetailDTO> orderDetails = createOrderRequestDTO.getOrderDetails();
        for (OrderDetailDTO detailDTO : orderDetails) {

            Product product = productRepository.findProductByProductId(detailDTO.getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(savedOrder);
            orderDetail.setProductId(product);
            orderDetail.setQuantity(detailDTO.getQuantity());
            orderDetail.setPrice(detailDTO.getQuantity() * productRepository.findProductByProductId(detailDTO.getProductId()).getPrice());
            orderDetailRepository.save(orderDetail);
        }

        //return savedOrder;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order saveOrder = orderRepository.save(Order.builder()
                .cname(orderDTO.getCname())
                        .phone(orderDTO.getPhone())
                .email(orderDTO.getEmail())
                .address(orderDTO.getAddress())
                .payment_method(orderDTO.getPayment_method())
                        .status("Pending...")
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
