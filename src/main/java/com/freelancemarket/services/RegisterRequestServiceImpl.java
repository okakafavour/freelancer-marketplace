package com.freelancemarket.services;

import com.freelancemarket.dtos.request.LoginRequest;
import com.freelancemarket.dtos.request.RegisterRequest;
import com.freelancemarket.dtos.response.LoginResponse;
import com.freelancemarket.dtos.response.RegisterResponse;
import com.freelancemarket.models.User;
import com.freelancemarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterRequestServiceImpl implements RegisterRequestService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        RegisterResponse response = new RegisterResponse();

        if(registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()){
            response.setMessage("Email is required");
            return response;
        }

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            response.setMessage("User with this email already exists");
            return response;
        }

        User user  = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);


        response.setEmail(registerRequest.getEmail());
        response.setMessage("User registered successfully");

        return response;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        LoginResponse response = new LoginResponse();

        var existingUser = userRepository.findByEmail(loginRequest.getEmail());
        if(existingUser.isEmpty()){
            response.setMessage("User not found");
            return response;
        }

        User user = existingUser.get();

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            response.setMessage("Wrong password");
            return response;
        }

        response.setToken(UUID.randomUUID().toString());
        response.setEmail(user.getEmail());
        response.setMessage("User logged in successfully");
        return response;
    }
}
