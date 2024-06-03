package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.DiamondMount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiamondMountRepository extends JpaRepository<DiamondMount, Integer> {
    DiamondMount findDiamondMountByMountId(int id);

//    @Modifying
//    @Transactional
//    @Query("UPDATE DiamondMount d SET d.mountName = :mountName, d.size = :size, d.type = :type, d.material = :material, d.basePrice = :basePrice WHERE d.mountId = :id")
//    Integer updateDiamondMountByMountId(@Param("id") int id,
//                                    @Param("mountName") String mountName,
//                                    @Param("size") float size,
//                                    @Param("type") String type,
//                                    @Param("material") String material,
//                                    @Param("basePrice") float basePrice);
}
