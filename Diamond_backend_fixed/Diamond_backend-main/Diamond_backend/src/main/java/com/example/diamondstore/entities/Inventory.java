package com.example.diamondstore.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationid")
    private int locationId;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product productId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "condition")
    private String condition;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "available")
    private boolean available;

    @Column(name = "update_date")
    private Date updateDate;
}
