package com.example.diamondstore.api;
import com.example.diamondstore.core.config.mail.EmailService;
import com.example.diamondstore.core.config.security.MyUserDetailsService;
import com.example.diamondstore.dto.*;
import com.example.diamondstore.entities.User;
import com.example.diamondstore.response.ApiResponse;
import com.example.diamondstore.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUser createUser) throws Exception {
        try{
            if(userService.isEmailDuplicated(createUser.getEmail())) {
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(false)
                        .message("Email is duplicated!")
                        .build());
            } else if (!createUser.getEmail().contains("@") || createUser.getPhone().length() != 10){
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(false)
                        .message("Create user fail! Email or PhoneNumber wrong format!")
                        .build());
            } else {
                User user = userService.register(createUser.getFullName(), createUser.getEmail(), createUser.getPassword(), createUser.getPhone(), createUser.getGender(), createUser.getDob());
                return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .message("Create user success!")
                        .data(user)
                        .build());
            }
        }catch (Exception e){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Create user fail! Error: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/alluser")
    public ResponseEntity<ApiResponse> getAllUser() throws Exception {
        List<User> userList = userService.userList();
        if(userList.isEmpty()){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("List user is empty!")
                    .build());
        }else{
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Get All User")
                    .data(userList)
                    .build());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserId(@PathVariable int id) throws Exception {
        Optional<User> user = userService.getUserId(id);
        if(user.isPresent()){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Get User By ID Success")
                    .data(user)
                    .build());
        }else{
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Get User By ID Fail")
                    .build());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int id, @RequestBody UpdateUser updateUser) {
        User user = userService.getUserById(id);
        if(!userService.getUserId(id).isPresent()){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Update fail! Can't find this user!")
                    .build());
        }
        else if(userService.isEmailDuplicated(updateUser.getEmail()) && !user.getEmail().equals(updateUser.getEmail())) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Email is duplicated!")
                    .build());
        }
        else if (!updateUser.getEmail().contains("@") || updateUser.getPhone().length() != 10){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Update user fail! Email or PhoneNumber wrong format!")
                    .build());
        }
        else{
            User upUser = userService.updateUser(updateUser, id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("User updated successfully")
                    .data(upUser)
                    .build());
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse> changeStatusUserByUserid(@PathVariable int id, @RequestBody Map<String, String> status) throws Exception {
        if(!userService.getUserId(id).isPresent()){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Can't find this user!")
                    .build());
        }else if (userService.updateStatusByUserid(status.get("status"), id)){
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Update user status successfully!")
                    .build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Update user status fail!")
                    .build());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPass(@RequestBody EmailDTO emailDTO){
        String response = userService.forgotPass(emailDTO.getEmail());

        if(!response.startsWith("Invalid")){
            response = "http://localhost:3000/api/user/reset-password?token=" + response;
            emailService.sendSimpleEmail(emailDTO.getEmail(), "Reset Password", "Link: " + response);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Create token success! Please check email.")
                    .build());
        }
        else{
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message(response)
                    .build());
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPass(@RequestParam String token, @RequestBody PasswordResetDTO passwordResetDTO) {
        String message = userService.resetPass(token, passwordResetDTO);
        boolean success = message.equals("Your password has been successfully updated.");
        return ResponseEntity.ok(ApiResponse.builder()
                .success(success)
                .message(message)
                .build());
    }




    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLogin userLogin) {
        UserLoginResponse response = userService.login(userLogin.getEmail(), userLogin.getPassword());
        String token = myUserDetailsService.loginToken(userLogin);
        if (response == null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Login fail!")
                    .build());
        }
        else{
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Login success!")
                    .data(new UserToken(response.getUserId(), response.getEmail(), token))
                    .build());
        }
    }
}
