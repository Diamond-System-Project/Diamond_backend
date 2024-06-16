package com.example.diamondstore.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiamondDTO {
    private String diamondName;
    private String imageUrl;
    private String origin;
    private float caratWeight;
    private String color;
    private String clarity;
    private String cut;
    private String shape;
    private float basePrice;
}
