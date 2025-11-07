package com.freelancemarket.models;

import com.freelancemarket.enums.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection= "Users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private Roles role;
    private String phoneNumber;
    private LocalDateTime createdAt = LocalDateTime.now();
}
