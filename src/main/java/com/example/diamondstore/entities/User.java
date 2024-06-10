package com.example.diamondstore.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "[User]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Email(regexp = "^[a-zA-Z][a-zA-Z0-9._%+-]*@gmail\\.[a-zA-Z]{2,}$", message = "Email must be a valid Gmail address and should not start with a digit")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "roleid", nullable = false)
    private Role roleid;

    @Column(name = "point_accumulation")
    private int point;

    @Column(name = "status")
    private String status;

    @Column(name = "type_login")
    private String typeLogin;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "token_password")
    private String tokenPassword;

    @Column(name = "token_create_date")
    private Date tokenCreateDate;
}
