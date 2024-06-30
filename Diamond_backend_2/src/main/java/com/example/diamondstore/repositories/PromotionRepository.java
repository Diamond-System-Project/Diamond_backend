package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Promotion findPromotionByPromotionId(int promotionId);

    @Query("SELECT CASE WHEN COUNT(pro) > 0 THEN true ELSE false END FROM Promotion pro WHERE pro.promotionName = ?1")
    boolean existsPromotionName(String promotionName);
}
