package com.example.diamondstore.services;

import com.example.diamondstore.entities.ProductDiamond;
import com.example.diamondstore.repositories.ProductDiamondRepository;
import com.example.diamondstore.services.interfaces.ProductDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDiamondServiceImpl implements ProductDiamondService {
    @Autowired
    private ProductDiamondRepository productDiamondRepository;

    @Override
    public List<ProductDiamond> productDiamondList() {
        return productDiamondRepository.findAll();
    }

    @Override
    public ProductDiamond getProductDiamondById(int id) {
        return productDiamondRepository.findProductDiamondByProductDiamondId(id);
    }
}
