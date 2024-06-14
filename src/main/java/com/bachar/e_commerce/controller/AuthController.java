package com.bachar.e_commerce.controller;


import com.bachar.e_commerce.model.JwtRequest;
import com.bachar.e_commerce.model.JwtResponse;
import com.bachar.e_commerce.security.JwtHelper;
import com.bachar.e_commerce.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        UserDetails userDetails = authService.getUserDetails(token);
        return ResponseEntity.ok()
                .body(userDetails);
    }






}
