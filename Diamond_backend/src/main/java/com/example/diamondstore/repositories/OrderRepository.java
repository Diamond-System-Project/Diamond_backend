package com.example.diamondstore.repositories;
import com.example.diamondstore.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderId(int orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.cname = :cname, o.address = :address, o.phone = :phone, o.email = :email, o.payment_method = :payment_method WHERE o.orderId = :orderId")
    void updateOrderbyMember(@Param("cname") String cname,
                             @Param("address") String address,
                             @Param("phone") String phone,
                             @Param("email") String email,
                             @Param("payment_method") String payment_method,
                             @Param("orderId") Integer orderId);
}
