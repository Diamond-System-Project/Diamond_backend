//package com.example.diamondstore.core.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // Kích hoạt bảo mật theo phương thức
//public class Security_C {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa bảo vệ CSRF để đơn giản hóa
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/api/comment/**").authenticated() // Yêu cầu xác thực cho tất cả các endpoint dưới /api/comment/
//                                .anyRequest().permitAll() // Cho phép tất cả các yêu cầu khác mà không cần xác thực
//                )
//                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/")); // Kích hoạt xác thực dựa trên form
//
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Định nghĩa bean để mã hóa mật khẩu bằng BCrypt
//    }
//
//    // Định nghĩa UserDetailsService của bạn ở đây nếu cần
//}
