package com.example.diamondstore.api;

import com.example.diamondstore.dto.WarrantyDTO;
import com.example.diamondstore.entities.Warranty;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.response.ApiResponse;
import com.example.diamondstore.services.interfaces.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warranty")
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;



    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addWarranty(@RequestBody WarrantyDTO warrantyDTO) {
        try {
            if(warrantyDTO.getStartDate().after(warrantyDTO.getEndDate())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("StartDate must before EndDate ~~")
                                .build());
            }
            else {
                Warranty savedWarranty = warrantyService.addWarranty(warrantyDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                        ApiResponse.builder()
                                .success(true)
                                .message("Warranty Created Successfully")
                                .data(savedWarranty)
                                .build());
            }
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Error: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("update/{warrantyId}")
    public ResponseEntity<ApiResponse> updateWarranty(@PathVariable Integer warrantyId, @RequestBody WarrantyDTO warrantyDTO) {
        try {
            if(warrantyDTO.getStartDate().after(warrantyDTO.getEndDate())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.builder()
                                .success(false)
                                .message("StartDate must before EndDate ~~")
                                .build());
            }
            else {
                Warranty savedWarranty = warrantyService.updateWarranty(warrantyId,warrantyDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                        ApiResponse.builder()
                                .success(true)
                                .message("Warranty Created Successfully")
                                .data(savedWarranty)
                                .build());
            }
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Error: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("/{warrantyId}")
    public ResponseEntity<ApiResponse> deleteWarranty(@PathVariable Integer warrantyId) {
        try {
            warrantyService.deleteWarranty(warrantyId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Warranty Deleted Successfully")
                            .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Error: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> viewAllWarranty() {
        try {
            List<Warranty> warranties = warrantyService.viewAllWarranty();
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("List of All Warranties")
                            .data(warranties)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/{warrantyId}")
    public ResponseEntity<ApiResponse> getWarrantyById(@PathVariable Integer warrantyId) {
        try {
            Warranty warranty = warrantyService.getWarrantyById(warrantyId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Warranty Retrieved Successfully")
                            .data(warranty)
                            .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Error: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> searchWarrantyByProductId(@PathVariable Integer productId) {
        try {
            List<Warranty> warranties = warrantyService.searchWarrantyByProductId(productId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("List of Warranties for Product ID: " + productId)
                            .data(warranties)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Internal Server Error: " + e.getMessage())
                            .build());
        }
    }
}
