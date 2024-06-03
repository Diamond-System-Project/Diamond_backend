
package com.example.diamondstore.services;

        import com.example.diamondstore.entities.Order;
        import com.example.diamondstore.repositories.OrderRepository;
        import com.example.diamondstore.services.interfaces.OrderService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(int orderid) {
        orderRepository.deleteById(orderid);
    }

    @Override
    public Optional<Order> getOrderId(int orderid) {
        return orderRepository.findById(orderid);
    }

    @Override
    public void updateOrderbyMember(String cname, String address, String phone, String email, String paymentMethod, Integer orderId) {
        orderRepository.updateOrderbyMember(cname, address, phone, email, paymentMethod, orderId);
    }
}
