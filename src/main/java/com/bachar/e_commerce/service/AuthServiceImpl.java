package com.bachar.e_commerce.service;

import com.bachar.e_commerce.model.JwtRequest;
import com.bachar.e_commerce.model.JwtResponse;
import com.bachar.e_commerce.security.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
    public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    public AuthServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }


    @Override
    public JwtResponse login(JwtRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            System.out.println("Heyyyyyy" + authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password " + e.getMessage(), e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        return JwtResponse.builder().username(userDetails.getUsername()).token(token).build();
    }

    @Override
    public UserDetails getUserDetails(String header) {
        String jwt = extractTokenFromHeader(header);
        if(jwt != null) {
            String username = jwtHelper.getUsernameFromToken(jwt);
            return userDetailsService.loadUserByUsername(username);
        }
        return null;
    }

    private String extractTokenFromHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("JWT Authentication Header {} is not a valid JWT token or it does not begin with Bearer ");
            return null;
        }
        return header.substring(7);
    }
}
