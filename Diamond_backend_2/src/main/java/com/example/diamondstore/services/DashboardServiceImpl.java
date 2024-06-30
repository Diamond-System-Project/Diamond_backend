package com.example.diamondstore.services;

import com.example.diamondstore.entities.Role;
import com.example.diamondstore.repositories.OrderRepository;
import com.example.diamondstore.repositories.RoleRepository;
import com.example.diamondstore.repositories.UserRepository;
import com.example.diamondstore.services.interfaces.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public int countMember(Role role) {
        Role memberRole = roleRepository.findById(5).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return userRepository.countMember(memberRole);
    }

    @Override
    public int countProcessingOrder() {
        return orderRepository.countProcessingOrder();
    }

    @Override
    public int countCompleteOrder() {
        return orderRepository.countCompleteOrder();
    }

    @Override
    public int countCancelOrder() {

        return orderRepository.countCancelOrder();
    }

    @Override
    public float totalRevenue() {
        return orderRepository.totalRevenue();
    }
}
