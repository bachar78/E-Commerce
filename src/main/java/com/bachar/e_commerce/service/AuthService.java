package com.bachar.e_commerce.service;

import com.bachar.e_commerce.model.JwtRequest;
import com.bachar.e_commerce.model.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

JwtResponse login(JwtRequest request);


    UserDetails getUserDetails(String token);
}
