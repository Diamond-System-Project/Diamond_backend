package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.ProductDiamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDiamondRepository extends JpaRepository<ProductDiamond, Integer> {
    ProductDiamond findProductDiamondByProductDiamondId(int id);
}
