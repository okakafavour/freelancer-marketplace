package com.freelancemarket.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String token;
    private String email;
    private String message;
}
