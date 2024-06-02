package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.userId = :userId")
    Integer updateStatusByUserId(@Param("status") String status, @Param("userId") int userId);
    User findUserByUserId(int id);
}
