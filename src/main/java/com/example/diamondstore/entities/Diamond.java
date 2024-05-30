package com.example.diamondstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Diamond")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diamondid")
    private int diamondId;

    @Column(name = "diamond_name", nullable = false)
    private String diamondName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "carat_weight")
    private float caratWeight;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "base_price")
    private float basePrice;
}
