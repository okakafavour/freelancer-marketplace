    package com.freelancemarket.utils;

    import com.freelancemarket.dtos.request.RegisterRequest;
    import com.freelancemarket.dtos.response.RegisterResponse;
    import com.freelancemarket.models.User;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    import java.time.LocalDateTime;

    public class Mapper {

        private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        public static User mapToUser(RegisterRequest registerRequest) {
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setFullName(registerRequest.getFullName());
            user.setRole(registerRequest.getRole());
            user.setPhoneNumber(registerRequest.getPhoneNumber());
            user.setCreatedAt(LocalDateTime.now());
            return user;
        }

        public static RegisterResponse mapToResponse(User user) {
            RegisterResponse response = new RegisterResponse();
            response.setEmail(user.getEmail());
            response.setMessage("User registered successfully");
            return response;
        }
    }
