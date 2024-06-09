package com.example.diamondstore.services.interfaces;

import com.example.diamondstore.entities.ProductDiamond;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDiamondService {
    List<ProductDiamond> productDiamondList();
    ProductDiamond getProductDiamondById(int id);
}
