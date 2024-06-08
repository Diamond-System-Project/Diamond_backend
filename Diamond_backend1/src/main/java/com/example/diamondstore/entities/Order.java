package com.example.diamondstore.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "[Order]")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Integer orderId;

    @Column(name = "customerid")
    private Integer cid;

    @NotBlank(message = "Customer name is required")
    @Column(name = "customer_name", nullable = false)
    private String cname;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phone;

    @Column(name = "address")
    private String address;

    @Email(regexp = ".+@gmail\\..+", message = "Email must be a valid Gmail address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @PastOrPresent(message = "Order date must be in the past or present")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "order_date")
    private Date order_date;

    @Column(name = "status")
    private String status;

    @Column(name = "voucherid")
    private Integer voucher;

    @Column(name = "payment_amount")
    private float payment;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "payment_date")
    private Date payment_date;

    @Column(name = "payment_method")
    private String payment_method;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "delivery_date")
    private Date delivery;

    @Column(name = "delivery_staffid")
    private Integer deli_staff;

    @PrePersist
    @PreUpdate
    public void validateDates() {
        if (payment_date != null && order_date != null && payment_date.before(order_date)) {
            throw new IllegalArgumentException("Payment date must be after the order date");
        }
        if (delivery != null && order_date != null && delivery.before(order_date)) {
            throw new IllegalArgumentException("Delivery date must be after the order date");
        }
    }
}
