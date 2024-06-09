package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.Product;
import com.example.diamondstore.entities.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {
    List<Warranty> findByProductId(Product productId);
    List<Warranty> findByProductIdAndStatus(Product productId, String status);
}
