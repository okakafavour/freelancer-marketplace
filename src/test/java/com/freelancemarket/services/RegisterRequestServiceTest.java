package com.freelancemarket.services;

import com.freelancemarket.dtos.request.LoginRequest;
import com.freelancemarket.dtos.request.RegisterRequest;
import com.freelancemarket.dtos.response.LoginResponse;
import com.freelancemarket.dtos.response.RegisterResponse;
import com.freelancemarket.enums.Roles;
import com.freelancemarket.models.User;
import com.freelancemarket.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterRequestServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterRequestService registerRequestService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testToRegisterAUser() {
        RegisterRequest register = new RegisterRequest();
        register.setFullName("okaka favour");
        register.setEmail("okakafavour81@gmail.com");
        register.setPassword("12345");
        register.setPhoneNumber("123456789");
        register.setRole(Roles.USER);

        RegisterResponse CreatedUser = registerRequestService.register(register);
        assertNotNull(CreatedUser);
        assertEquals("okakafavour81@gmail.com", CreatedUser.getEmail());
        assertEquals("User registered successfully", CreatedUser.getMessage());

        User savedUser = userRepository.findByEmail("okakafavour81@gmail.com").get();
        assertTrue(passwordEncoder.matches("12345", savedUser.getPassword()));

    }

    @Test
    public void testThatEmailAlreadyExists() {
        RegisterRequest register = new RegisterRequest();
        register.setFullName("okaka favour");
        register.setEmail("okakafavour81@gmail.com");
        register.setPassword("12345");
        register.setPhoneNumber("123456789");
        register.setRole(Roles.USER);
        userRepository.save(register);

        RegisterRequest register2 = new RegisterRequest();
        register2.setFullName("okaka favour");
        register2.setEmail("okakafavour81@gmail.com");
        register2.setPassword("12345");
        RegisterResponse CreatedUser = registerRequestService.register(register2);
        assertNotNull(CreatedUser);
        assertEquals("User with this email already exists", CreatedUser.getMessage());
    }

    @Test
    public void testToRegisterWithoutEmail() {
        RegisterRequest register = new RegisterRequest();
        register.setFullName("david sam");
        register.setPassword("0000");
        register.setPhoneNumber("0987654321");
        register.setRole(Roles.USER);

        RegisterResponse CreatedUser = registerRequestService.register(register);
        assertNotNull(CreatedUser);
        assertEquals("Email is required",  CreatedUser.getMessage());
    }

    @Test
    public void testToLogin(){
        RegisterRequest register = new RegisterRequest();
        register.setFullName("okaka favour");
        register.setEmail("okakafavour81@gmail.com");
        register.setPassword("12345");
        register.setPhoneNumber("123456789");
        register.setRole(Roles.USER);
        registerRequestService.register(register);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("12345");
        loginRequest.setEmail("okakafavour81@gmail.com");

        LoginResponse loginResponse = registerRequestService.login(loginRequest);
        assertNotNull(loginResponse);
        assertEquals("User logged in successfully", loginResponse.getMessage());
        assertEquals("okakafavour81@gmail.com",  loginResponse.getEmail());
    }

    @Test
    public void testToLoginWithWrongPassword_throwsException(){
        RegisterRequest register = new RegisterRequest();
        register.setFullName("dabo gilo");
        register.setEmail("okakafavour81@gmail.com");
        register.setPassword("12345");
        register.setPhoneNumber("123456789");
        register.setRole(Roles.USER);
        registerRequestService.register(register);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("0000");
        loginRequest.setEmail("okakafavour81@gmail.com");

        LoginResponse response = registerRequestService.login(loginRequest);
        assertNotNull(response);
        assertEquals("Incorrect password", response.getMessage());
    }
}