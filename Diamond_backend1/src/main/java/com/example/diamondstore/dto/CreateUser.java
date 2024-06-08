package com.example.diamondstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUser {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private Date dob;
}
