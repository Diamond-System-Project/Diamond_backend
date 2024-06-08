package com.example.diamondstore.services;

import com.example.diamondstore.dto.WarrantyDTO;
import com.example.diamondstore.entities.Warranty;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.repositories.ProductRepository;
import com.example.diamondstore.repositories.WarrantyRepository;
import com.example.diamondstore.services.interfaces.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarrantyServiceImp implements WarrantyService {

    @Autowired
    private WarrantyRepository warrantyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Warranty addWarranty(WarrantyDTO warrantyDTO) {
        Warranty saveWarranty = warrantyRepository.save(Warranty.builder()
                        .productId(productRepository.findProductByProductId(warrantyDTO.getProductId()))
                                .warrantyLength(warrantyDTO.getWarrantyLength())
                        .startDate(warrantyDTO.getStartDate())
                        .endDate(warrantyDTO.getEndDate())
                        .status(warrantyDTO.getStatus())
                .build());
        return saveWarranty;
    }

    @Override
    public Warranty updateWarranty(Integer warrantyId, WarrantyDTO warrantyDTO) throws DataNotFoundException {
        Warranty existingWarranty = warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found!"));

//        existingWarranty.setProductId(productRepository.findProductByProductId(warrantyDTO.getProductId()));
        existingWarranty.setWarrantyLength(warrantyDTO.getWarrantyLength());
        existingWarranty.setStartDate(warrantyDTO.getStartDate());
        existingWarranty.setEndDate(warrantyDTO.getEndDate());
        existingWarranty.setStatus(warrantyDTO.getStatus());



        return warrantyRepository.save(existingWarranty);

    }

    @Override
    public void deleteWarranty(Integer warrantyId) throws DataNotFoundException {
        Warranty existingWarranty = warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found"));
        warrantyRepository.delete(existingWarranty);
    }

    @Override
    public List<Warranty> viewAllWarranty() {
        return warrantyRepository.findAll();
    }

    @Override
    public Warranty getWarrantyById(Integer warrantyId) throws DataNotFoundException {
        return warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found"));
    }

    @Override
    public List<Warranty> searchWarrantyByProductId(Integer productId) {
        return warrantyRepository.findByProductId(productRepository.findProductByProductId(productId));
    }
}
