package com.example.diamondstore.services;

import com.example.diamondstore.dto.WarrantyDTO;
import com.example.diamondstore.entities.Warranty;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.repositories.ProductRepository;
import com.example.diamondstore.repositories.WarrantyRepository;
import com.example.diamondstore.services.interfaces.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class WarrantyServiceImp implements WarrantyService {

    @Autowired
    private WarrantyRepository warrantyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Warranty addWarranty(WarrantyDTO warrantyDTO) {

        List<Warranty> activeWarranties = warrantyRepository.findByProductIdAndStatus(
                productRepository.findProductByProductId(warrantyDTO.getProductId()), "Is_active");

        if (!activeWarranties.isEmpty()) {
            throw new IllegalArgumentException("This ProductId already has an active warranty.");
        }

        Warranty warranty = Warranty.builder()
                .productId(productRepository.findProductByProductId(warrantyDTO.getProductId()))
                .warrantyLength(warrantyDTO.getWarrantyLength())
                .startDate(warrantyDTO.getStartDate())
                .endDate(warrantyDTO.getEndDate())
                .status(getWarrantyStatus(warrantyDTO.getStartDate(), warrantyDTO.getEndDate()))
                .build();
        return warrantyRepository.save(warranty);
    }

    @Override
    public Warranty updateWarranty(Integer warrantyId, WarrantyDTO warrantyDTO) throws DataNotFoundException {
        Warranty existingWarranty = warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found!"));

        if ("Is_active".equals(existingWarranty.getStatus())) {
            // Kiểm tra nếu đã có Warranty với trạng thái Is_active cho productId này
            List<Warranty> activeWarranties = warrantyRepository.findByProductIdAndStatus(
                    productRepository.findProductByProductId(warrantyDTO.getProductId()), "Is_active");

            if (!activeWarranties.isEmpty() && activeWarranties.stream().noneMatch(w -> Integer.valueOf(w.getWarrantyId()).equals(warrantyId))) {
                throw new IllegalArgumentException("Product already has an active warranty.");
            }
        }

        existingWarranty.setProductId(productRepository.findProductByProductId(warrantyDTO.getProductId()));
        existingWarranty.setWarrantyLength(warrantyDTO.getWarrantyLength());
        existingWarranty.setStartDate(warrantyDTO.getStartDate());
        existingWarranty.setEndDate(warrantyDTO.getEndDate());

        LocalDate today = LocalDate.now();
        LocalDate startDate = warrantyDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = warrantyDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (today.isAfter(startDate) && today.isBefore(endDate) || today.equals(startDate) || today.equals(endDate)) {
            existingWarranty.setStatus("Is_active");
        } else if ("Is_active".equals(existingWarranty.getStatus())) {
            throw new IllegalArgumentException("Cannot set status to Is_active!! Please try again!!");
        }

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

    private String getWarrantyStatus(Date startDate, Date endDate) {
        Date currentDate = new Date();
        if (currentDate.after(startDate) && currentDate.before(endDate)) {
            return "Is_active";
        }
        return "Inactive";
    }
}
