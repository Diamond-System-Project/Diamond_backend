package com.example.diamondstore.services;

import com.example.diamondstore.dto.WarrantyDTO;
import com.example.diamondstore.entities.Warranty;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.repositories.ProductRepository;
import com.example.diamondstore.repositories.WarrantyRepository;
import com.example.diamondstore.services.interfaces.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                productRepository.findProductByProductId(warrantyDTO.getProductId()), "Active");

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

    private String getWarrantyStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(startDate)) {
            return "InActive";
        } else if (currentDate.isAfter(endDate)) {
            return "Expired";
        } else {
            return "Active";
        }
    }

    @Override
    public Warranty updateWarranty(Integer warrantyId, WarrantyDTO warrantyDTO) throws DataNotFoundException {
        Warranty existingWarranty = warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found!"));

        // Không cho phép đổi productId
        if (existingWarranty.getProductId().getProductId() != warrantyDTO.getProductId()) {
            throw new IllegalArgumentException("Cannot change the product of the warranty.");
        }

        // Kiểm tra nếu đã có Warranty với trạng thái Is_active cho productId này
        List<Warranty> activeWarranties = warrantyRepository.findByProductIdAndStatus(
                productRepository.findProductByProductId(warrantyDTO.getProductId()), "Active");

        // Kiểm tra nếu có bảo hành khác đang ở trạng thái Is_active
        if (!activeWarranties.isEmpty() && activeWarranties.stream().noneMatch(w -> Integer.valueOf(w.getWarrantyId()).equals(warrantyId))) {
            throw new IllegalArgumentException("Product already has an active warranty.");
        }

        // Cập nhật các thuộc tính khác của bảo hành
        existingWarranty.setWarrantyLength(warrantyDTO.getWarrantyLength());
        existingWarranty.setStartDate(warrantyDTO.getStartDate());
        existingWarranty.setEndDate(warrantyDTO.getEndDate());

        // Cập nhật trạng thái dựa trên ngày hiện tại
        existingWarranty.setStatus(getWarrantyStatus(warrantyDTO.getStartDate(), warrantyDTO.getEndDate()));

        return warrantyRepository.save(existingWarranty);
    }


    @Override
    public void deleteWarranty(Integer warrantyId) throws DataNotFoundException {
        Warranty existingWarranty = warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new DataNotFoundException("Warranty not found"));
        if ("Active".equals(existingWarranty.getStatus())) {
            throw new IllegalArgumentException("Cannot delete active warranty.");
        }
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

    // Hàm updateStatus để tự động cập nhật trạng thái
    @Scheduled(cron = "0 0 0 * * ?")  // Chạy hàng ngày vào lúc nửa đêm
    public void updateStatus() {
        List<Warranty> warranties = warrantyRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Warranty warranty : warranties) {
            LocalDate startDate = warranty.getStartDate();
            LocalDate endDate = warranty.getEndDate();
            if (currentDate.isBefore(startDate)) {
                warranty.setStatus("InActive");
            } else if (currentDate.isAfter(endDate)) {
                warranty.setStatus("Expired");
            } else {
                warranty.setStatus("Active");
            }
            warrantyRepository.save(warranty);
        }
    }
}
