package com.example.diamondstore.services.interfaces;

import com.example.diamondstore.entities.Role;

public interface DashboardService {
    int countMember(Role role);

    int countProcessingOrder();

    int countCompleteOrder();

    int countCancelOrder();

    float totalRevenue();
}
