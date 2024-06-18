package com.example.diamondstore.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAthenticationEntryPoint point;

    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/*/*/login", "/*/*/logout", "/*/*/register", "/*/*/payment/*",
                                "/*/*/forgot-password", "/*/*/reset-password",
                                "/*/*/createWithDetails", "/*/comment/product/*",
                                "/*/*/allproduct", "/*/product/search/*").permitAll() // Permit access to /login endpoint

                        .requestMatchers("/api/comment/add", "/api/comment/edit/*", "/api/order/createWithDetails", "/api/order/update", "/api/order_detail/create", "/api/order_detail/update").hasRole("Member") //Comment
                        .requestMatchers("/api/certificate/*", "/api/diamond/*", "/api/mount/*", "/api/inventory/*", "/api/promotion/*", "/api/order_detail/orderDetail", "/api/order_detail/all", "/api/order_detail/order"
                            , "/api/productdiamond/*", "/api/productprice/*", "/api/productpromotion/*", "/api/order/update" ,"/api/order/update/user",
                                "api/order/delivery/status", "/api/product/showProduct","/api/product/create","/api/product/update", "/api/product/showProduct/list" ).hasRole("Manager")
                        .requestMatchers("/api/order/delivery", "/api/order/delivery/status" ).hasRole("Delivery Staff")


                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
