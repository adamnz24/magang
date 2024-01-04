package com.test.service;

import com.test.config.JwtUtil;
import com.test.dto.AuthenticationRequest;
import com.test.dto.AuthenticationResponse;
import com.test.dto.RegisterRequest;
import com.test.enums.Role;
import com.test.entity.User;
import com.test.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AuthenticationService {

    private final AdminRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(AdminRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        String firstname = request.getFirstname();
        String lastname = request.getLastname();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(firstname, lastname, email, username, password, Role.ADMIN);
        userRepository.save(user);


        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtUtil.generateToken(user, new HashMap<>());
        return new AuthenticationResponse(token);
    }
}