package com.example.diamondstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUser {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Date dob;
    private String gender;
}
