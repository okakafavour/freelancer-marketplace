package com.freelancemarket.services;

import com.freelancemarket.dtos.request.LoginRequest;
import com.freelancemarket.dtos.request.RegisterRequest;
import com.freelancemarket.dtos.response.LoginResponse;
import com.freelancemarket.dtos.response.RegisterResponse;

public interface RegisterRequestService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
